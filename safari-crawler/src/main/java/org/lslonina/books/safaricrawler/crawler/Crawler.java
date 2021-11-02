package org.lslonina.books.safaricrawler.crawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lslonina.books.safaricrawler.dto.Book;
import org.lslonina.books.safaricrawler.dto.BookBuilder;
import org.lslonina.books.safaricrawler.dto.BookChangeLogger;
import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.QueryResult;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.lslonina.books.safaricrawler.repository.BookRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookDetailsRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(Crawler.class);
    public static final String BASE = "https://learning.oreilly.com/api/v2/search/";
    public static final String LIMIT = "200";
    public static final String SORT_BY_DATE_ADDED = "date_added";
    public static final String SORT_BY_PUBLICATION_DATE = "publication_date";
    public static final String SORT_BY = SORT_BY_DATE_ADDED;
    private final Set<String> ignoredIds = new HashSet<>();
    private final Set<String> ignoredIsbns = new HashSet<>();
    private final Set<String> selectedIds = new HashSet<>();
    private final Set<String> selectedIsbns = new HashSet<>();

    private static String createQueryBooksAddress(int page) {
        return BASE + "?sort=" + SORT_BY + "&query=*&limit=" + LIMIT + "&include_case_studies=true&include_courses=true&include_orioles=true&include_playlists=true&include_collections=true&collection_type=expert&collection_sharing=public&collection_sharing=enterprise&exclude_fields=description&page=" + page + "&formats=book";
    }

    private final RestTemplate restTemplate;

    private final SafariBookRepository safariBookRepository;
    private final SafariBookDetailsRepository safariBookDetailsRepository;
    private final BookRepository bookRepository;

    public Crawler(RestTemplate restTemplate, SafariBookRepository safariBookRepository,
                   SafariBookDetailsRepository safariBookDetailsRepository, BookRepository bookCoverRepository) throws IOException {
        this.restTemplate = restTemplate;
        this.safariBookRepository = safariBookRepository;
        this.safariBookDetailsRepository = safariBookDetailsRepository;
        this.bookRepository = bookCoverRepository;

//        ignoredIds = new HashSet<>(Files.readAllLines(Path.of("ignored.csv-id.csv")));
//        ignoredIsbns = new HashSet<>(Files.readAllLines(Path.of("ignored.csv-isbn.csv")));
//        selectedIds = new HashSet<>(Files.readAllLines(Path.of("selected-id.csv")));
//        selectedIsbns = new HashSet<>(Files.readAllLines(Path.of("selected-isbn.csv")));
//        ignoredIds.remove(null);
//        ignoredIsbns.remove(null);
//        selectedIds.remove(null);
//        selectedIsbns.remove(null);
    }

    public void loadData() {
        int page = 0;
        while (true) {
            try {
                log.info("Loading data for page: " + page);
                List<SafariBook> safariBooks = getSafariBooks(page);
                if (safariBooks.isEmpty()) {
                    log.info("Finished loading books, pages: " + page);
                    return;
                }
                processSafariBooks(safariBooks);
                page++;
            } catch (Exception e) {
                log.error("Error while processing page {}", page, e);
            }
        }
    }

    private void processSafariBooks(List<SafariBook> safariBooks) {
        List<SafariBook> existingSafariBooks = safariBookRepository.findAllByArchiveIdIn(safariBooks.stream().map(SafariBook::getArchiveId).collect(Collectors.toSet()));
        Set<String> existingIds = existingSafariBooks.stream().map(SafariBook::getArchiveId).collect(Collectors.toSet());
        log.info("Existing books: " + existingSafariBooks.size());
        //TODO: compare existing books for changes
        List<SafariBook> newBooks = safariBooks.stream().filter(b -> !existingIds.contains(b.getArchiveId())).collect(Collectors.toList());
        safariBookRepository.saveAll(newBooks);

        //TODO: check if full reload required
        List<SafariBookDetails> safariBookDetails = safariBookDetailsRepository.findAllByIdentifierIn(existingIds);
        Set<String> safariBookDetailIds = safariBookDetails.stream().map(SafariBookDetails::getIdentifier).collect(Collectors.toSet());
        List<SafariBook> booksWithoutBookDetails = safariBooks.stream().filter(b -> !safariBookDetailIds.contains(b.getArchiveId())).collect(Collectors.toList());

        log.info("Fetching details for books: " + booksWithoutBookDetails.size());
        List<SafariBookDetails> newSafariBookDetails = getBookDetails(booksWithoutBookDetails);

        try {
            safariBookDetailsRepository.saveAll(newSafariBookDetails);
        } catch (Exception e) {
            log.error("Can't store at once", e);
            for (SafariBookDetails newSafariBookDetail : newSafariBookDetails) {
                try {
                    safariBookDetailsRepository.save(newSafariBookDetail);
                } catch (RuntimeException ex) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String json = mapper.writeValueAsString(newSafariBookDetail);
                        log.error("Error storing: " + json);
                    } catch (JsonProcessingException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }
        safariBookDetails.addAll(newSafariBookDetails);

        List<Book> existingBooks = bookRepository.findAllByIdentifierIn(existingIds);
        List<Book> books = createBooks(safariBooks, safariBookDetails, existingBooks);
        bookRepository.saveAll(books);
    }

    private List<SafariBook> getSafariBooks(int page) {
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

    private List<SafariBookDetails> getBookDetails(List<SafariBook> safariBooks) {
        List<SafariBookDetails> list = new ArrayList<>();
        for (SafariBook safariBook : safariBooks) {
            try {
                SafariBookDetails forObject = restTemplate.getForObject(safariBook.getUrl(), SafariBookDetails.class);
                forObject.setPublisherResourceLinks(null);
                list.add(forObject);
            } catch (RuntimeException e) {
                log.error("Can't fetch details for: " + safariBook.getTitle());
            }
        }
        return list;
    }

    private List<Book> createBooks(List<SafariBook> safariBooks, List<SafariBookDetails> safariBookDetails, List<Book> existingBooks) {
        Map<String, SafariBookDetails> bookDetailsMap = safariBookDetails.stream()
                .collect(Collectors.toMap(SafariBookDetails::getIdentifier, details -> details));

        Map<String, Book> existingBooksMap = existingBooks.stream()
                .collect(Collectors.toMap(Book::getIdentifier, details -> details));

        List<Book> list = new ArrayList<>();
        for (SafariBook safariBook : safariBooks) {
            SafariBookDetails details = bookDetailsMap.get(safariBook.getArchiveId());
            if (details != null) {
                Book existingBook = existingBooksMap.get(safariBook.getArchiveId());
                String cover = existingBook != null ? existingBook.getCover().equals("") ? getCover(safariBook) : existingBook.getCover() : getCover(safariBook);
                Book book = createBook(safariBook, details, existingBook, cover);
                if (book != null && book.getPriority() != -1) {
                    BookChangeLogger.logChanges(existingBook, book);
                }
                if (book != null) {
                    list.add(book);
                }
            }
        }
        return list;
    }

    private Book createBook(SafariBook safariBook, SafariBookDetails details, Book existingBook, String coverData) {
        String added = safariBook.getDateAdded();
        Date dateAdded = added != null ? Date.from(Instant.parse(added)) : null;
        String issued = safariBook.getIssued();
        Date datePublished = issued != null ? Date.from(Instant.parse(issued)) : null;
        String modified = safariBook.getLastModifiedTime();
        Date dateModified = modified != null ? Date.from(Instant.parse(modified)) : null;
        int pageCount = details.getPageCount() != null ? details.getPageCount() : -1;
        int priority = existingBook != null ? existingBook.getPriority() : 0;
        if (priority == 0) {
            if (selectedIds.contains(safariBook.getArchiveId()) || selectedIsbns.contains(safariBook.getIsbn())) {
                priority = 1;
            } else if (ignoredIds.contains(safariBook.getArchiveId()) || ignoredIsbns.contains(safariBook.getIsbn())) {
                priority = -1;
            }
        }

        BookBuilder bookBuilder = new BookBuilder(safariBook.getArchiveId())
                .withTitle(safariBook.getTitle())
                .withPublishers(safariBook.getPublishers())
                .withAuthors(safariBook.getAuthors())
                .withDescription(details.getDescription())
                .withPages(pageCount)
                .withCover(coverData)
                .withPriority(priority)
                .withAdded(dateAdded)
                .withPublished(datePublished)
                .withModified(dateModified)
                .withIsbn(safariBook.getIsbn())
                .withLanguage(details.getLanguage());
        Book newBook = bookBuilder.build();

        return Objects.equals(newBook, existingBook) ? null : newBook;
    }

    private String getCover(SafariBook safariBook) {
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
}
