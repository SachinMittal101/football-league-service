package com.sapient.demo.footballleague.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class TeamStandingDto implements Serializable {

    private final String countryId;
    private final String countryName;
    private final String leagueId;
    private final String leagueName;
    private final String teamId;
    private final String teamName;
    private final String overallLeaguePosition;

    public TeamStandingDto(final String countryId,
                           final String countryName,
                           final String leagueId,
                           final String leagueName,
                           final String teamId,
                           final String teamName,
                           final String overallLeaguePosition) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.overallLeaguePosition = overallLeaguePosition;
    }

}
