package com.sapient.demo.footballleague.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
public class Country {

    private String countryId;
    private String countryName;

    @JsonCreator
    public Country(@JsonProperty("country_id") final String countryId,
                   @JsonProperty("country_name") final String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }
}
