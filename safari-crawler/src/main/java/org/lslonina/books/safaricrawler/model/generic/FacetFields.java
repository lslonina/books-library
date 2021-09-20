
package org.lslonina.books.safaricrawler.model.generic;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "format",
        "subjects",
        "topic_uuids",
        "publishers_exact",
        "language",
        "sharing_status",
        "expert_playlist",
        "topics"
})
public class FacetFields {

    @JsonProperty("format")
    private List<String> format = null;
    @JsonProperty("subjects")
    private List<String> subjects = null;
    @JsonProperty("topic_uuids")
    private List<String> topicUuids = null;
    @JsonProperty("publishers_exact")
    private List<String> publishersExact = null;
    @JsonProperty("language")
    private List<String> language = null;
    @JsonProperty("sharing_status")
    private List<String> sharingStatus = null;
    @JsonProperty("expert_playlist")
    private List<String> expertPlaylist = null;
    @JsonProperty("topics")
    private List<String> topics = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("format")
    public List<String> getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(List<String> format) {
        this.format = format;
    }

    @JsonProperty("subjects")
    public List<String> getSubjects() {
        return subjects;
    }

    @JsonProperty("subjects")
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    @JsonProperty("topic_uuids")
    public List<String> getTopicUuids() {
        return topicUuids;
    }

    @JsonProperty("topic_uuids")
    public void setTopicUuids(List<String> topicUuids) {
        this.topicUuids = topicUuids;
    }

    @JsonProperty("publishers_exact")
    public List<String> getPublishersExact() {
        return publishersExact;
    }

    @JsonProperty("publishers_exact")
    public void setPublishersExact(List<String> publishersExact) {
        this.publishersExact = publishersExact;
    }

    @JsonProperty("language")
    public List<String> getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(List<String> language) {
        this.language = language;
    }

    @JsonProperty("sharing_status")
    public List<String> getSharingStatus() {
        return sharingStatus;
    }

    @JsonProperty("sharing_status")
    public void setSharingStatus(List<String> sharingStatus) {
        this.sharingStatus = sharingStatus;
    }

    @JsonProperty("expert_playlist")
    public List<String> getExpertPlaylist() {
        return expertPlaylist;
    }

    @JsonProperty("expert_playlist")
    public void setExpertPlaylist(List<String> expertPlaylist) {
        this.expertPlaylist = expertPlaylist;
    }

    @JsonProperty("topics")
    public List<String> getTopics() {
        return topics;
    }

    @JsonProperty("topics")
    public void setTopics(List<String> topics) {
        this.topics = topics;
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
