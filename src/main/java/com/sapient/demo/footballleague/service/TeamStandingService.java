package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.dto.TeamStandingDto;
import com.sapient.demo.footballleague.util.NotFoundException;

public interface TeamStandingService {

    public TeamStandingDto getTeamStandingDetails(final String countryName, final String leagueName, final String teamName) throws NotFoundException;

    public TeamStandingDto getTeamStanding(final String countryId, final String leagueId, final String teamName) throws NotFoundException;
}
