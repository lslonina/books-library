package org.lslonina.books.safaricrawler.crawler;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.lslonina.books.safaricrawler.service.OreillyBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(Crawler.class);

    private final OreillyClient booksClient;
    private final OreillyBookService bookService;
    private final BooksProcessor booksProcessor;

    public Crawler(OreillyClient booksClient, OreillyBookService oreillyBookService, BooksProcessor bookProcessor) throws IOException {
        this.booksClient = booksClient;
        this.bookService = oreillyBookService;
        this.booksProcessor = bookProcessor;
    }

    public void loadData() {
        int page = 0;
        while (true) {
            try {
                log.info("Loading data for page: " + page);
                List<SafariBook> safariBooks = booksClient.getSafariBooks(page);
                if (safariBooks.isEmpty()) {
                    log.info("Finished loading books, pages: " + page);
                    return;
                }

                Set<String> ids = processSafariBooks(safariBooks);
                booksProcessor.updateBooks(ids);
        
                page++;
            } catch (Exception e) {
                log.error("Error while processing page {}", page, e);
            }
        }
    }

    private Set<String> processSafariBooks(List<SafariBook> safariBooks) {
        Set<String> archiveIds = safariBooks.stream().map(SafariBook::getArchiveId).collect(Collectors.toSet());
        Collection<SafariBook> existingSafariBooks = bookService.findAllBooksByIdentifierIn(archiveIds);
        Set<String> existingIds = existingSafariBooks.stream().map(SafariBook::getArchiveId).collect(Collectors.toSet());
        log.info("Existing books: " + existingSafariBooks.size());
        //TODO: compare existing books for changes
        List<SafariBook> newBooks = safariBooks.stream().filter(b -> !existingIds.contains(b.getArchiveId())).collect(Collectors.toList());
        bookService.saveBooks(newBooks);

        //TODO: check if full reload required
        Collection<SafariBookDetails> safariBookDetails = bookService.findAllBooksDetailsByIdentifierIn(existingIds);
        Set<String> safariBookDetailIds = safariBookDetails.stream().map(SafariBookDetails::getIdentifier).collect(Collectors.toSet());
        List<SafariBook> booksWithoutBookDetails = safariBooks.stream().filter(b -> !safariBookDetailIds.contains(b.getArchiveId())).collect(Collectors.toList());

        log.info("Fetching details for books: " + booksWithoutBookDetails.size());
        List<SafariBookDetails> newSafariBookDetails = booksClient.getBookDetails(booksWithoutBookDetails);
        bookService.saveBooksDetails(newSafariBookDetails);

        return archiveIds;
    }
}
