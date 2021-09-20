
package org.lslonina.books.safaricrawler.model.generic;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "average_rating"
})
public class FacetIntervals {

    @JsonProperty("average_rating")
    private AverageRating averageRating;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("average_rating")
    public AverageRating getAverageRating() {
        return averageRating;
    }

    @JsonProperty("average_rating")
    public void setAverageRating(AverageRating averageRating) {
        this.averageRating = averageRating;
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
