package org.lslonina.books.safaricrawler.crawler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.lslonina.books.safaricrawler.dto.Book;
import org.lslonina.books.safaricrawler.dto.BookChangeLogger;
import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.lslonina.books.safaricrawler.repository.BookRepository;
import org.lslonina.books.safaricrawler.service.OreillyBookService;

public class BooksProcessor {
    private final OreillyBookService oreillyBookService;
    private final BookRepository bookRepository;
    private final BookFactory bookFactory;

    public BooksProcessor(OreillyBookService oreillyBookService, BookRepository bookRepository, BookFactory bookFactory) {
        this.oreillyBookService = oreillyBookService;
        this.bookRepository = bookRepository;
        this.bookFactory = bookFactory;
    }

    public void updateBooks(Set<String> ids) {
        Collection<SafariBook> processedSafariBooks = oreillyBookService.findAllBooksByIdentifierIn(ids);
        Collection<SafariBookDetails> safariBookDetails = oreillyBookService.findAllBooksDetailsByIdentifierIn(ids);
        List<Book> existingBooks = bookRepository.findAllByIdentifierIn(ids);
        List<Book> books = createBooks(processedSafariBooks, safariBookDetails, existingBooks);
        bookRepository.saveAll(books);
    }

    private List<Book> createBooks(Collection<SafariBook> safariBooks, Collection<SafariBookDetails> safariBookDetails, List<Book> existingBooks) {
        Map<String, SafariBookDetails> bookDetailsMap = safariBookDetails.stream()
                .collect(Collectors.toMap(SafariBookDetails::getIdentifier, details -> details));

        Map<String, Book> existingBooksMap = existingBooks.stream()
                .collect(Collectors.toMap(Book::getIdentifier, details -> details));

        List<Book> list = new ArrayList<>();
        for (SafariBook safariBook : safariBooks) {
            SafariBookDetails details = bookDetailsMap.get(safariBook.getArchiveId());
            if (details != null) {
                Book existingBook = existingBooksMap.get(safariBook.getArchiveId());
                String cover = details.getCover();
                Book book = bookFactory.createBook(safariBook, details, existingBook, cover);
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

}
