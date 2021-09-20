package org.lslonina.books.safaricrawler.dto;

import java.util.Date;
import java.util.List;

public class BookBuilder {
    private final String identifier;
    private String title;
    private List<String> authors;
    private String description;
    private int pages;
    private String cover;
    private int priority;
    private Date added;
    private Date published;
    private Date modified;
    private List<String> publishers;
    private String isbn;
    private String language;

    public BookBuilder(String identifier) {
        this.identifier = identifier;
    }

    public BookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder withAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public BookBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public BookBuilder withPages(int pages) {
        this.pages = pages;
        return this;
    }

    public BookBuilder withCover(String cover) {
        this.cover = cover;
        return this;
    }

    public BookBuilder withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public BookBuilder withAdded(Date added) {
        this.added = added;
        return this;
    }

    public BookBuilder withPublished(Date published) {
        this.published = published;
        return this;
    }

    public BookBuilder withModified(Date modified) {
        this.modified = modified;
        return this;
    }

    public BookBuilder withPublishers(List<String> publishers) {
        this.publishers = publishers;
        return this;
    }

    public BookBuilder withIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    public Book build() {
        Book book = new Book(identifier, title, authors, publishers, description, pages, cover, priority, added, published, modified, isbn, language);
        book.setModificationTimestamp(new Date());
        return book;
    }
}