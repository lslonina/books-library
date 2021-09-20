
package org.lslonina.books.safaricrawler.model.generic;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "{!ex=format}(format:video AND title_unstemmed:\"Learning Path: .*\")",
        "enterprise_playlists",
        "total_playlists",
        "non_enterprise_expert_playlists"
})
public class FacetQueries {

    @JsonProperty("{!ex=format}(format:video AND title_unstemmed:\"Learning Path: .*\")")
    private Integer exFormatFormatVideoANDTitleUnstemmedLearningPath;
    @JsonProperty("enterprise_playlists")
    private Integer enterprisePlaylists;
    @JsonProperty("total_playlists")
    private Integer totalPlaylists;
    @JsonProperty("non_enterprise_expert_playlists")
    private Integer nonEnterpriseExpertPlaylists;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("{!ex=format}(format:video AND title_unstemmed:\"Learning Path: .*\")")
    public Integer getExFormatFormatVideoANDTitleUnstemmedLearningPath() {
        return exFormatFormatVideoANDTitleUnstemmedLearningPath;
    }

    @JsonProperty("{!ex=format}(format:video AND title_unstemmed:\"Learning Path: .*\")")
    public void setExFormatFormatVideoANDTitleUnstemmedLearningPath(Integer exFormatFormatVideoANDTitleUnstemmedLearningPath) {
        this.exFormatFormatVideoANDTitleUnstemmedLearningPath = exFormatFormatVideoANDTitleUnstemmedLearningPath;
    }

    @JsonProperty("enterprise_playlists")
    public Integer getEnterprisePlaylists() {
        return enterprisePlaylists;
    }

    @JsonProperty("enterprise_playlists")
    public void setEnterprisePlaylists(Integer enterprisePlaylists) {
        this.enterprisePlaylists = enterprisePlaylists;
    }

    @JsonProperty("total_playlists")
    public Integer getTotalPlaylists() {
        return totalPlaylists;
    }

    @JsonProperty("total_playlists")
    public void setTotalPlaylists(Integer totalPlaylists) {
        this.totalPlaylists = totalPlaylists;
    }

    @JsonProperty("non_enterprise_expert_playlists")
    public Integer getNonEnterpriseExpertPlaylists() {
        return nonEnterpriseExpertPlaylists;
    }

    @JsonProperty("non_enterprise_expert_playlists")
    public void setNonEnterpriseExpertPlaylists(Integer nonEnterpriseExpertPlaylists) {
        this.nonEnterpriseExpertPlaylists = nonEnterpriseExpertPlaylists;
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
