package org.lslonina.books.safaricrawler.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Book {
    @Id
    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("title")
    private String title;

    @JsonProperty("authors")
    private List<String> authors;

    @JsonProperty("publishers")
    private List<String> publishers;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("language")
    private String language;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pages")
    private int pages;

    @JsonProperty("cover")
    private String cover;

    @JsonProperty("priority")
    private int priority;

    @JsonProperty("added")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date added;

    @JsonProperty("published")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date published;

    @JsonProperty("updated")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updated;

    @JsonProperty("modificationTimestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modificationTimestamp;

    public Book(String identifier, String title, List<String> authors, List<String> publishers, String description, int pages, String cover, int priority, Date added, Date published, Date updated, String isbn, String language) {
        this.identifier = identifier;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.pages = pages;
        this.cover = cover;
        this.priority = priority;
        this.added = added;
        this.published = published;
        this.updated = updated;
        this.publishers = publishers;
        this.isbn = isbn;
        this.language = language;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages &&
                priority == book.priority &&
                Objects.equals(identifier, book.identifier) &&
                Objects.equals(title, book.title) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(publishers, book.publishers) &&
                Objects.equals(description, book.description) &&
                Objects.equals(cover, book.cover) &&
                Objects.equals(added, book.added) &&
                Objects.equals(published, book.published) &&
                Objects.equals(updated, book.updated) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(language, book.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, title, authors, publishers, description, pages, cover, priority, added, published, updated, isbn, language);
    }

    @Override
    public String toString() {
        return identifier + "," + isbn + ",\"" + title + "\",\"" + published + "\",\"" + authors + "\"," + modificationTimestamp + "," + priority ;
    }
}
