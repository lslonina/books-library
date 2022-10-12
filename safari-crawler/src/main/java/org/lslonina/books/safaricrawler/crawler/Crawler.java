package org.lslonina.books.safaricrawler.crawler;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.lslonina.books.safaricrawler.dto.Book;
import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.lslonina.books.safaricrawler.service.BookService;
import org.lslonina.books.safaricrawler.service.OreillyBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(Crawler.class);

    private final OreillyClient booksClient;
    private final OreillyBookService oreillyBookService;
    private final BookService bookService;
    private final BooksProcessor booksProcessor;

    public Crawler(OreillyClient booksClient, OreillyBookService oreillyBookService, BookService bookService,
            BooksProcessor bookProcessor) throws IOException {
        this.booksClient = booksClient;
        this.oreillyBookService = oreillyBookService;
        this.bookService = bookService;
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
                int updated = booksProcessor.updateBooks(ids);
                if (updated < 1) {
                    log.info("All books already loaded, use full reload.");
                    return;
                }
                page++;
            } catch (Exception e) {
                log.error("Error while processing page {}", page, e);
            }
        }
    }

    public void refreshCovers() {
        var pageNumber = 0;
        Page<Book> page;
        do {
            var pageRequest = PageRequest.of(pageNumber, 200, Sort.by("published").descending());
            page = bookService.findSelected(pageRequest);
            List<Book> books = page.getContent();

            var ids = updateCovers(books);
            booksProcessor.updateBooks(ids);

            log.info("Processed covers for: {}", page.getNumber());
            pageNumber++;
        }
        while (!page.isEmpty());
    }

    private Set<String> updateCovers(List<Book> books) {
        var ids = books.stream().map(b -> b.getIdentifier()).collect(Collectors.toSet());
        var booksDetails = oreillyBookService.findAllBooksDetailsByIdentifierIn(ids).stream().collect(Collectors.toList());
        Map<String, SafariBookDetails> bookDetailsMap = booksDetails.stream()
                .collect(Collectors.toMap(SafariBookDetails::getIdentifier, details -> details));

        var safariBooks = oreillyBookService.findAllBooksByIdentifierIn(ids);
        for (SafariBook safariBook : safariBooks) {
            var coverData = booksClient.getCover(safariBook);
            var details = bookDetailsMap.get(safariBook.getArchiveId());
            details.setCover(coverData);
            oreillyBookService.saveBooksDetails(booksDetails);
            log.info("processing cover for: " + safariBook.getTitle());
        }
        return ids;
    }

    private Set<String> processSafariBooks(List<SafariBook> safariBooks) {
        Set<String> archiveIds = safariBooks.stream().map(SafariBook::getArchiveId).collect(Collectors.toSet());
        Set<String> existingIds = getExistingBooks(archiveIds);
        List<SafariBook> newBooks = safariBooks.stream().filter(b -> !existingIds.contains(b.getArchiveId()))
                .collect(Collectors.toList());
        oreillyBookService.saveBooks(newBooks);

        // TODO: check if full reload required
        Collection<SafariBookDetails> safariBookDetails = oreillyBookService.findAllBooksDetailsByIdentifierIn(existingIds);
        Set<String> safariBookDetailIds = safariBookDetails.stream().map(SafariBookDetails::getIdentifier)
                .collect(Collectors.toSet());
        List<SafariBook> booksWithoutBookDetails = safariBooks.stream()
                .filter(b -> !safariBookDetailIds.contains(b.getArchiveId())).collect(Collectors.toList());

        log.info("Fetching details for books: " + booksWithoutBookDetails.size());
        List<SafariBookDetails> newSafariBookDetails = booksClient.getBookDetails(booksWithoutBookDetails);
        oreillyBookService.saveBooksDetails(newSafariBookDetails);

        return archiveIds;
    }

    private Set<String> getExistingBooks(Set<String> archiveIds) {
        Collection<SafariBook> existingSafariBooks = oreillyBookService.findAllBooksByIdentifierIn(archiveIds);
        Set<String> existingIds = existingSafariBooks.stream().map(SafariBook::getArchiveId)
                .collect(Collectors.toSet());
        log.info("Existing books: " + existingSafariBooks.size());
        return existingIds;
    }
}
