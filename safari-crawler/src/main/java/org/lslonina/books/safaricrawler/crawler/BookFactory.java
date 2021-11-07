package org.lslonina.books.safaricrawler.crawler;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.lslonina.books.safaricrawler.dto.Book;
import org.lslonina.books.safaricrawler.dto.BookBuilder;
import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookFactory {
    private static final Logger log = LoggerFactory.getLogger(Crawler.class);

    private final Map<String, Integer> processedBooks;

    public BookFactory(Map<String, Integer> processedBooks) {
        this.processedBooks = processedBooks;
    }

    public Book createBook(SafariBook safariBook, SafariBookDetails details, Book existingBook, String coverData) {
        String added = safariBook.getDateAdded();
        Date dateAdded = added != null ? Date.from(Instant.parse(added)) : null;
        String issued = safariBook.getIssued();
        Date datePublished = issued != null ? Date.from(Instant.parse(issued)) : null;
        String modified = safariBook.getLastModifiedTime();
        Date dateModified = modified != null ? Date.from(Instant.parse(modified)) : null;
        int pageCount = details.getPageCount() != null ? details.getPageCount() : -1;
        int priority = existingBook != null ? existingBook.getPriority() : 0;
        if (priority == 0) {
            if (processedBooks.containsKey(safariBook.getArchiveId())) {
                priority = processedBooks.get(safariBook.getArchiveId());
                log.info("Book {} already processed, with priority {}", safariBook.getTitle(), priority);
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

}
