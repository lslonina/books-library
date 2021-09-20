
package org.lslonina.books.safaricrawler.model.generic;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "no_stars",
        "zero_stars",
        "one_star",
        "two_stars",
        "three_stars",
        "four_stars",
        "five_stars"
})
public class AverageRating {

    @JsonProperty("no_stars")
    private Integer noStars;
    @JsonProperty("zero_stars")
    private Integer zeroStars;
    @JsonProperty("one_star")
    private Integer oneStar;
    @JsonProperty("two_stars")
    private Integer twoStars;
    @JsonProperty("three_stars")
    private Integer threeStars;
    @JsonProperty("four_stars")
    private Integer fourStars;
    @JsonProperty("five_stars")
    private Integer fiveStars;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("no_stars")
    public Integer getNoStars() {
        return noStars;
    }

    @JsonProperty("no_stars")
    public void setNoStars(Integer noStars) {
        this.noStars = noStars;
    }

    @JsonProperty("zero_stars")
    public Integer getZeroStars() {
        return zeroStars;
    }

    @JsonProperty("zero_stars")
    public void setZeroStars(Integer zeroStars) {
        this.zeroStars = zeroStars;
    }

    @JsonProperty("one_star")
    public Integer getOneStar() {
        return oneStar;
    }

    @JsonProperty("one_star")
    public void setOneStar(Integer oneStar) {
        this.oneStar = oneStar;
    }

    @JsonProperty("two_stars")
    public Integer getTwoStars() {
        return twoStars;
    }

    @JsonProperty("two_stars")
    public void setTwoStars(Integer twoStars) {
        this.twoStars = twoStars;
    }

    @JsonProperty("three_stars")
    public Integer getThreeStars() {
        return threeStars;
    }

    @JsonProperty("three_stars")
    public void setThreeStars(Integer threeStars) {
        this.threeStars = threeStars;
    }

    @JsonProperty("four_stars")
    public Integer getFourStars() {
        return fourStars;
    }

    @JsonProperty("four_stars")
    public void setFourStars(Integer fourStars) {
        this.fourStars = fourStars;
    }

    @JsonProperty("five_stars")
    public Integer getFiveStars() {
        return fiveStars;
    }

    @JsonProperty("five_stars")
    public void setFiveStars(Integer fiveStars) {
        this.fiveStars = fiveStars;
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
