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
public class League {

    private final String countryId;
    private final String countryName;
    private final String leagueId;
    private final String leagueName;

    @JsonCreator
    public League(@JsonProperty("country_id") final String countryId,
                  @JsonProperty("country_name") final String countryName,
                  @JsonProperty("league_id") final String leagueId,
                  @JsonProperty("league_name") final String leagueName) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.leagueId = leagueId;
        this.leagueName = leagueName;
    }
}
