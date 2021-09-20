
package org.lslonina.books.safaricrawler.model.details;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "url",
    "natural_key",
    "authors",
    "subjects",
    "topics",
    "publishers",
    "chapters",
    "cover",
    "chapter_list",
    "toc",
    "flat_toc",
    "web_url",
    "last_chapter_read",
    "academic_excluded",
    "opf_unique_identifier_type",
    "has_mathml",
    "created_time",
    "last_modified_time",
    "identifier",
    "name",
    "title",
    "format",
    "content_format",
    "source",
    "orderable_title",
    "has_stylesheets",
    "description",
    "isbn",
    "issued",
    "language",
    "rights",
    "updated",
    "orderable_author",
    "purchase_link",
    "publisher_resource_links",
    "is_free",
    "is_system_book",
    "is_active",
    "is_hidden",
    "virtual_pages",
    "duration_seconds",
    "pagecount"
})
public class SafariBookDetails {

    @JsonProperty("url")
    private String url;
    @JsonProperty("natural_key")
    private List<String> naturalKey = null;
    @JsonProperty("authors")
    private List<Author> authors = null;
    @JsonProperty("subjects")
    private List<Object> subjects = null;
    @JsonProperty("topics")
    private List<Topic> topics = null;
    @JsonProperty("publishers")
    private List<Publisher> publishers = null;
    @JsonProperty("chapters")
    private List<String> chapters = null;
    @JsonProperty("cover")
    private String cover;
    @JsonProperty("chapter_list")
    private String chapterList;
    @JsonProperty("toc")
    private String toc;
    @JsonProperty("flat_toc")
    private String flatToc;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("last_chapter_read")
    private Object lastChapterRead;
    @JsonProperty("academic_excluded")
    private Boolean academicExcluded;
    @JsonProperty("opf_unique_identifier_type")
    private String opfUniqueIdentifierType;
    @JsonProperty("has_mathml")
    private Boolean hasMathml;
    @JsonProperty("created_time")
    private String createdTime;
    @JsonProperty("last_modified_time")
    private String lastModifiedTime;

    @Id
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("title")
    private String title;
    @JsonProperty("format")
    private String format;
    @JsonProperty("content_format")
    private String contentFormat;
    @JsonProperty("source")
    private String source;
    @JsonProperty("orderable_title")
    private String orderableTitle;
    @JsonProperty("has_stylesheets")
    private Boolean hasStylesheets;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("issued")
    private String issued;
    @JsonProperty("language")
    private String language;
    @JsonProperty("rights")
    private String rights;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("orderable_author")
    private String orderableAuthor;
    @JsonProperty("purchase_link")
    private Object purchaseLink;
    @JsonProperty("publisher_resource_links")
    private PublisherResourceLinks publisherResourceLinks;
    @JsonProperty("is_free")
    private Boolean isFree;
    @JsonProperty("is_system_book")
    private Boolean isSystemBook;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("is_hidden")
    private Boolean isHidden;
    @JsonProperty("virtual_pages")
    private Integer virtualPages;
    @JsonProperty("duration_seconds")
    private Object durationSeconds;
    @JsonProperty("pagecount")
    private Integer pagecount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("natural_key")
    public List<String> getNaturalKey() {
        return naturalKey;
    }

    @JsonProperty("natural_key")
    public void setNaturalKey(List<String> naturalKey) {
        this.naturalKey = naturalKey;
    }

    @JsonProperty("authors")
    public List<Author> getAuthors() {
        return authors;
    }

    @JsonProperty("authors")
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @JsonProperty("subjects")
    public List<Object> getSubjects() {
        return subjects;
    }

    @JsonProperty("subjects")
    public void setSubjects(List<Object> subjects) {
        this.subjects = subjects;
    }

    @JsonProperty("topics")
    public List<Topic> getTopics() {
        return topics;
    }

    @JsonProperty("topics")
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @JsonProperty("publishers")
    public List<Publisher> getPublishers() {
        return publishers;
    }

    @JsonProperty("publishers")
    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    @JsonProperty("chapters")
    public List<String> getChapters() {
        return chapters;
    }

    @JsonProperty("chapters")
    public void setChapters(List<String> chapters) {
        this.chapters = chapters;
    }

    @JsonProperty("cover")
    public String getCover() {
        return cover;
    }

    @JsonProperty("cover")
    public void setCover(String cover) {
        this.cover = cover;
    }

    @JsonProperty("chapter_list")
    public String getChapterList() {
        return chapterList;
    }

    @JsonProperty("chapter_list")
    public void setChapterList(String chapterList) {
        this.chapterList = chapterList;
    }

    @JsonProperty("toc")
    public String getToc() {
        return toc;
    }

    @JsonProperty("toc")
    public void setToc(String toc) {
        this.toc = toc;
    }

    @JsonProperty("flat_toc")
    public String getFlatToc() {
        return flatToc;
    }

    @JsonProperty("flat_toc")
    public void setFlatToc(String flatToc) {
        this.flatToc = flatToc;
    }

    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }

    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @JsonProperty("last_chapter_read")
    public Object getLastChapterRead() {
        return lastChapterRead;
    }

    @JsonProperty("last_chapter_read")
    public void setLastChapterRead(Object lastChapterRead) {
        this.lastChapterRead = lastChapterRead;
    }

    @JsonProperty("academic_excluded")
    public Boolean getAcademicExcluded() {
        return academicExcluded;
    }

    @JsonProperty("academic_excluded")
    public void setAcademicExcluded(Boolean academicExcluded) {
        this.academicExcluded = academicExcluded;
    }

    @JsonProperty("opf_unique_identifier_type")
    public String getOpfUniqueIdentifierType() {
        return opfUniqueIdentifierType;
    }

    @JsonProperty("opf_unique_identifier_type")
    public void setOpfUniqueIdentifierType(String opfUniqueIdentifierType) {
        this.opfUniqueIdentifierType = opfUniqueIdentifierType;
    }

    @JsonProperty("has_mathml")
    public Boolean getHasMathml() {
        return hasMathml;
    }

    @JsonProperty("has_mathml")
    public void setHasMathml(Boolean hasMathml) {
        this.hasMathml = hasMathml;
    }

    @JsonProperty("created_time")
    public String getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("last_modified_time")
    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    @JsonProperty("last_modified_time")
    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
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

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("orderable_title")
    public String getOrderableTitle() {
        return orderableTitle;
    }

    @JsonProperty("orderable_title")
    public void setOrderableTitle(String orderableTitle) {
        this.orderableTitle = orderableTitle;
    }

    @JsonProperty("has_stylesheets")
    public Boolean getHasStylesheets() {
        return hasStylesheets;
    }

    @JsonProperty("has_stylesheets")
    public void setHasStylesheets(Boolean hasStylesheets) {
        this.hasStylesheets = hasStylesheets;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
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

    @JsonProperty("rights")
    public String getRights() {
        return rights;
    }

    @JsonProperty("rights")
    public void setRights(String rights) {
        this.rights = rights;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("orderable_author")
    public String getOrderableAuthor() {
        return orderableAuthor;
    }

    @JsonProperty("orderable_author")
    public void setOrderableAuthor(String orderableAuthor) {
        this.orderableAuthor = orderableAuthor;
    }

    @JsonProperty("purchase_link")
    public Object getPurchaseLink() {
        return purchaseLink;
    }

    @JsonProperty("purchase_link")
    public void setPurchaseLink(Object purchaseLink) {
        this.purchaseLink = purchaseLink;
    }

    @JsonProperty("publisher_resource_links")
    public PublisherResourceLinks getPublisherResourceLinks() {
        return publisherResourceLinks;
    }

    @JsonProperty("publisher_resource_links")
    public void setPublisherResourceLinks(PublisherResourceLinks publisherResourceLinks) {
        this.publisherResourceLinks = publisherResourceLinks;
    }

    @JsonProperty("is_free")
    public Boolean getIsFree() {
        return isFree;
    }

    @JsonProperty("is_free")
    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    @JsonProperty("is_system_book")
    public Boolean getIsSystemBook() {
        return isSystemBook;
    }

    @JsonProperty("is_system_book")
    public void setIsSystemBook(Boolean isSystemBook) {
        this.isSystemBook = isSystemBook;
    }

    @JsonProperty("is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    @JsonProperty("is_active")
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("is_hidden")
    public Boolean getIsHidden() {
        return isHidden;
    }

    @JsonProperty("is_hidden")
    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    @JsonProperty("virtual_pages")
    public Integer getVirtualPages() {
        return virtualPages;
    }

    @JsonProperty("virtual_pages")
    public void setVirtualPages(Integer virtualPages) {
        this.virtualPages = virtualPages;
    }

    @JsonProperty("duration_seconds")
    public Object getDurationSeconds() {
        return durationSeconds;
    }

    @JsonProperty("duration_seconds")
    public void setDurationSeconds(Object durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    @JsonProperty("pagecount")
    public Integer getPageCount() {
        return pagecount;
    }

    @JsonProperty("pagecount")
    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
