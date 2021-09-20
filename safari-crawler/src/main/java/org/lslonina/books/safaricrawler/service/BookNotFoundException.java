package org.lslonina.books.safaricrawler.service;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String bookId) {
        super("Could not find book: " + bookId);
    }
}
