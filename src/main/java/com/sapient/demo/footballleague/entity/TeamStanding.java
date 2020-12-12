package com.sapient.demo.footballleague.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
public class TeamStanding {

    private String countryId;
    private final String countryName;
    private final String leagueId;
    private final String leagueName;
    private final String teamId;
    private final String teamName;
    private final String overallLeaguePosition;

    @JsonCreator
    public TeamStanding(@JsonProperty("country_id") final String countryId,
                        @JsonProperty("country_name") final String countryName,
                        @JsonProperty("league_id") final String leagueId,
                        @JsonProperty("league_name") final String leagueName,
                        @JsonProperty("team_id") final String teamId,
                        @JsonProperty("team_name") final String teamName,
                        @JsonProperty("overall_league_position") final String overallLeaguePosition) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.overallLeaguePosition = overallLeaguePosition;
    }

}
