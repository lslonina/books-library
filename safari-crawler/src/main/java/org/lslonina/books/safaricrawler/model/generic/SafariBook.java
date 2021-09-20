
package org.lslonina.books.safaricrawler.model.generic;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "authors",
        "last_modified_time",
        "format",
        "content_format",
        "archive_id",
        "isbn",
        "issued",
        "language",
        "publishers",
        "title",
        "url",
        "virtual_pages",
        "web_url",
        "content_type",
        "source",
        "academic_excluded",
        "ourn",
        "duration_seconds",
        "has_assessment",
        "timestamp",
        "average_rating",
        "number_of_followers",
        "number_of_items",
        "number_of_reviews",
        "popularity",
        "report_score",
        "cover_url",
        "date_added",
        "topics",
        "topics_payload"
})
public class SafariBook {

    @JsonProperty("id")
    private String id;
    @JsonProperty("authors")
    private List<String> authors = null;
    @JsonProperty("last_modified_time")
    private String lastModifiedTime;
    @JsonProperty("format")
    private String format;
    @JsonProperty("content_format")
    private String contentFormat;

    @Id
    @JsonProperty("archive_id")
    private String archiveId;

    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("issued")
    private String issued;
    @JsonProperty("language")
    private String language;
    @JsonProperty("publishers")
    private List<String> publishers = null;
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("virtual_pages")
    private Integer virtualPages;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("source")
    private String source;
    @JsonProperty("academic_excluded")
    private Boolean academicExcluded;
    @JsonProperty("ourn")
    private String ourn;
    @JsonProperty("duration_seconds")
    private Integer durationSeconds;
    @JsonProperty("has_assessment")
    private Boolean hasAssessment;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("average_rating")
    private Integer averageRating;
    @JsonProperty("number_of_followers")
    private Integer numberOfFollowers;
    @JsonProperty("number_of_items")
    private Integer numberOfItems;
    @JsonProperty("number_of_reviews")
    private Integer numberOfReviews;
    @JsonProperty("popularity")
    private Integer popularity;
    @JsonProperty("report_score")
    private Integer reportScore;
    @JsonProperty("cover_url")
    private String coverUrl;
    @JsonProperty("date_added")
    private String dateAdded;
    @JsonProperty("topics")
    private List<String> topics = null;
    @JsonProperty("topics_payload")
    private List<TopicsPayload> topicsPayload = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("authors")
    public List<String> getAuthors() {
        return authors;
    }

    @JsonProperty("authors")
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @JsonProperty("last_modified_time")
    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    @JsonProperty("last_modified_time")
    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    @JsonProperty("content_format")
    public String getContentFormat() {
        return contentFormat;
    }

    @JsonProperty("content_format")
    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    @JsonProperty("archive_id")
    public String getArchiveId() {
        return archiveId;
    }

    @JsonProperty("archive_id")
    public void setArchiveId(String archiveId) {
        this.archiveId = archiveId;
    }

    @JsonProperty("isbn")
    public String getIsbn() {
        return isbn;
    }

    @JsonProperty("isbn")
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @JsonProperty("issued")
    public String getIssued() {
        return issued;
    }

    @JsonProperty("issued")
    public void setIssued(String issued) {
        this.issued = issued;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("publishers")
    public List<String> getPublishers() {
        return publishers;
    }

    @JsonProperty("publishers")
    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("virtual_pages")
    public Integer getVirtualPages() {
        return virtualPages;
    }

    @JsonProperty("virtual_pages")
    public void setVirtualPages(Integer virtualPages) {
        this.virtualPages = virtualPages;
    }

    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }

    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @JsonProperty("content_type")
    public String getContentType() {
        return contentType;
    }

    @JsonProperty("content_type")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("academic_excluded")
    public Boolean getAcademicExcluded() {
        return academicExcluded;
    }

    @JsonProperty("academic_excluded")
    public void setAcademicExcluded(Boolean academicExcluded) {
        this.academicExcluded = academicExcluded;
    }

    @JsonProperty("ourn")
    public String getOurn() {
        return ourn;
    }

    @JsonProperty("ourn")
    public void setOurn(String ourn) {
        this.ourn = ourn;
    }

    @JsonProperty("duration_seconds")
    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    @JsonProperty("duration_seconds")
    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    @JsonProperty("has_assessment")
    public Boolean getHasAssessment() {
        return hasAssessment;
    }

    @JsonProperty("has_assessment")
    public void setHasAssessment(Boolean hasAssessment) {
        this.hasAssessment = hasAssessment;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("average_rating")
    public Integer getAverageRating() {
        return averageRating;
    }

    @JsonProperty("average_rating")
    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    @JsonProperty("number_of_followers")
    public Integer getNumberOfFollowers() {
        return numberOfFollowers;
    }

    @JsonProperty("number_of_followers")
    public void setNumberOfFollowers(Integer numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    @JsonProperty("number_of_items")
    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    @JsonProperty("number_of_items")
    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @JsonProperty("number_of_reviews")
    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    @JsonProperty("number_of_reviews")
    public void setNumberOfReviews(Integer numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    @JsonProperty("popularity")
    public Integer getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("report_score")
    public Integer getReportScore() {
        return reportScore;
    }

    @JsonProperty("report_score")
    public void setReportScore(Integer reportScore) {
        this.reportScore = reportScore;
    }

    @JsonProperty("cover_url")
    public String getCoverUrl() {
        return coverUrl;
    }

    @JsonProperty("cover_url")
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @JsonProperty("date_added")
    public String getDateAdded() {
        return dateAdded;
    }

    @JsonProperty("date_added")
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @JsonProperty("topics")
    public List<String> getTopics() {
        return topics;
    }

    @JsonProperty("topics")
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @JsonProperty("topics_payload")
    public List<TopicsPayload> getTopicsPayload() {
        return topicsPayload;
    }

    @JsonProperty("topics_payload")
    public void setTopicsPayload(List<TopicsPayload> topicsPayload) {
        this.topicsPayload = topicsPayload;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", archiveId='" + archiveId + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SafariBook that = (SafariBook) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(lastModifiedTime, that.lastModifiedTime) &&
                Objects.equals(format, that.format) &&
                Objects.equals(contentFormat, that.contentFormat) &&
                Objects.equals(archiveId, that.archiveId) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(issued, that.issued) &&
                Objects.equals(language, that.language) &&
                Objects.equals(publishers, that.publishers) &&
                Objects.equals(title, that.title) &&
                Objects.equals(url, that.url) &&
                Objects.equals(virtualPages, that.virtualPages) &&
                Objects.equals(webUrl, that.webUrl) &&
                Objects.equals(contentType, that.contentType) &&
                Objects.equals(source, that.source) &&
                Objects.equals(academicExcluded, that.academicExcluded) &&
                Objects.equals(ourn, that.ourn) &&
                Objects.equals(durationSeconds, that.durationSeconds) &&
                Objects.equals(hasAssessment, that.hasAssessment) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(averageRating, that.averageRating) &&
                Objects.equals(numberOfFollowers, that.numberOfFollowers) &&
                Objects.equals(numberOfItems, that.numberOfItems) &&
                Objects.equals(numberOfReviews, that.numberOfReviews) &&
                Objects.equals(popularity, that.popularity) &&
                Objects.equals(reportScore, that.reportScore) &&
                Objects.equals(coverUrl, that.coverUrl) &&
                Objects.equals(dateAdded, that.dateAdded) &&
                Objects.equals(topics, that.topics) &&
                Objects.equals(topicsPayload, that.topicsPayload) &&
                Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authors, lastModifiedTime, format, contentFormat, archiveId, isbn, issued, language, publishers, title, url, virtualPages, webUrl, contentType, source, academicExcluded, ourn, durationSeconds, hasAssessment, timestamp, averageRating, numberOfFollowers, numberOfItems, numberOfReviews, popularity, reportScore, coverUrl, dateAdded, topics, topicsPayload, additionalProperties);
    }
}
