package com.sapient.demo.footballleague.controller;

import com.sapient.demo.footballleague.dto.TeamStandingDto;
import com.sapient.demo.footballleague.service.TeamStandingService;
import com.sapient.demo.footballleague.util.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TeamStandingController {

    @Autowired
    private TeamStandingService teamStandingService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public TeamStandingDto getTeamStandings(@RequestParam("country_name") final String countryName,
                                            @RequestParam("league_name") final String leagueName,
                                            @RequestParam("team_name") final String teamName) throws NotFoundException {
        log.info("received request for get team standings by country name : {}, league name : {} and team name : {}",
                countryName, leagueName, teamName);
        return teamStandingService.getTeamStandingDetails(countryName, leagueName, teamName);
    }
}
