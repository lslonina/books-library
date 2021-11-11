package org.lslonina.books.safaricrawler.crawler;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.QueryResult;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class OreillyClient {
    private static final Logger log = LoggerFactory.getLogger(OreillyClient.class);

    private static final String BASE = "https://learning.oreilly.com/api/v2/search/";
    private static final String LIMIT = "200";
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
        ExecutorService threadPool = Executors.newFixedThreadPool(12);
        CountDownLatch latch = new CountDownLatch(safariBooks.size());
        List<SafariBookDetails> result = Collections.synchronizedList(new ArrayList<>());
        for (SafariBook safariBook : safariBooks) {
            threadPool.submit(() -> {
                try {
                    SafariBookDetails bookDetails = restTemplate.getForObject(safariBook.getUrl(),
                            SafariBookDetails.class);
                    bookDetails.setPublisherResourceLinks(null);
                    String cover = getCover(safariBook);
                    bookDetails.setCover(cover);
                    result.add(bookDetails);
                } catch (Exception e) {
                    log.error("Can't fetch details for: {}", safariBook.getTitle());
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException exception) {
            log.error("Failed fetching details: ", exception);
        }
        threadPool.shutdownNow();
        return result;
    }

    public String getCover(SafariBook safariBook) {
        String coverUrl = safariBook.getCoverUrl() + "400w";
        try {
            byte[] imageBytes = restTemplate.getForObject(coverUrl, byte[].class);
            String imageAsString = Base64.getEncoder().encodeToString(imageBytes);
            if (imageAsString.isBlank()) {
                log.warn("Can't fetch cover [blank] for: {}, {}", safariBook.getTitle(), coverUrl);
            }
            return imageAsString;
        } catch (Exception ex) {
            log.warn("Can't fetch cover [exception] for: {}, {}", safariBook.getTitle(), coverUrl);
            ex.printStackTrace();
        }
        return "";
    }

    private static String createQueryBooksAddress(int page) {
        return BASE + "?sort=" + SORT_BY + "&query=*&limit=" + LIMIT
                + "&include_case_studies=true&include_courses=true&include_orioles=true&include_playlists=true&include_collections=true&collection_type=expert&collection_sharing=public&collection_sharing=enterprise&exclude_fields=description&page="
                + page + "&formats=book";
    }
}
