package org.lslonina.books.safaricrawler.crawler;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.QueryResult;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class OreillyClient {
    private static final Logger log = LoggerFactory.getLogger(OreillyClient.class);
  
    private static final String BASE = "https://learning.oreilly.com/api/v2/search/";
    private static final String LIMIT = "10";
    private static final String SORT_BY_DATE_ADDED = "date_added";
    private static final String SORT_BY_PUBLICATION_DATE = "publication_date";
    private static final String SORT_BY = SORT_BY_DATE_ADDED;

    private final RestTemplate restTemplate;

    public OreillyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SafariBook> getSafariBooks(int page) {
        String address = createQueryBooksAddress(page);
        log.info("Fetching page: " + page + ", " + address);
        QueryResult queryResult = restTemplate.getForObject(address, QueryResult.class);
        List<SafariBook> safariBooks = queryResult.getSafariBooks();
        for (SafariBook safariBook : safariBooks) {
            String id = safariBook.getId();
            String fixedId = id.replace(".", "#");
            safariBook.setId(fixedId);
        }
        return safariBooks;
    }

    public List<SafariBookDetails> getBookDetails(List<SafariBook> safariBooks) {
        List<SafariBookDetails> result = new ArrayList<>();
        for (SafariBook safariBook : safariBooks) {
            try {
                SafariBookDetails bookDetails = restTemplate.getForObject(safariBook.getUrl(), SafariBookDetails.class);
                bookDetails.setPublisherResourceLinks(null);
                String cover = getCover(safariBook);
                bookDetails.setCover(cover);
                result.add(bookDetails);
            } catch (RuntimeException e) {
                log.error("Can't fetch details for: " + safariBook.getTitle());
            }
        }
        return result;
    }

    public String getCover(SafariBook safariBook) {
        try {
            byte[] imageBytes = restTemplate.getForObject(safariBook.getCoverUrl(), byte[].class);
            String imageAsString = Base64.getEncoder().encodeToString(imageBytes);
            if (imageAsString.isBlank()) {
                log.warn("Can't fetch cover for: " + safariBook.getTitle() + ", " + safariBook.getCoverUrl());
            }
            return imageAsString;
        } catch (Exception ex) {
            log.warn("Can't fetch cover for: " + safariBook.getTitle() + ", " + safariBook.getCoverUrl());
        }
        return "";
    }

    private static String createQueryBooksAddress(int page) {
        return BASE + "?sort=" + SORT_BY + "&query=*&limit=" + LIMIT + "&include_case_studies=true&include_courses=true&include_orioles=true&include_playlists=true&include_collections=true&collection_type=expert&collection_sharing=public&collection_sharing=enterprise&exclude_fields=description&page=" + page + "&formats=book";
    }


}
