package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.dto.TeamStandingDto;
import com.sapient.demo.footballleague.entity.Country;
import com.sapient.demo.footballleague.entity.League;
import com.sapient.demo.footballleague.entity.TeamStanding;
import com.sapient.demo.footballleague.util.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.sapient.demo.footballleague.util.ServiceConstants.*;

@Service
@Slf4j
public class TeamStandingServiceImpl implements TeamStandingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LeagueService leagueService;

    @Override
    public TeamStandingDto getTeamStandingDetails(final String countryName, final String leagueName,
                                                  final String teamName) throws NotFoundException {
        if (StringUtils.isEmpty(countryName) || StringUtils.isEmpty(leagueName) || StringUtils.isEmpty(teamName)) {
            throw new IllegalArgumentException("field can not be empty");
        } else {
            final Country country = countryService.getCountryByName(countryName);
            final League league = getLeagueByName(leagueName, country.getCountryId());
            return getTeamStanding(country.getCountryId(), league.getLeagueId(), teamName);
        }
    }

    @Override
    public TeamStandingDto getTeamStanding(final String countryId, final String leagueId, final String teamName) throws NotFoundException {
        Map<String, String> uriVariables = getUriVariables(leagueId, "get_standings", API_KEY);
        log.info("sending request to get team standing with league id : {}", leagueId);
        final TeamStanding[] teamStandings = restTemplate.getForEntity(BASE_URL + "?action={action}&league_id={league_id}&APIkey={APIkey}",
                TeamStanding[].class, uriVariables).getBody();
        final TeamStanding teamStanding = Arrays.stream(teamStandings).filter(team -> team.getTeamName().equalsIgnoreCase(teamName))
                .findFirst().orElseThrow(() -> new NotFoundException("a requested resource not found"));
        return getTeamStandingDto(countryId, teamStanding);
    }

    private TeamStandingDto getTeamStandingDto(final String countryId, final TeamStanding teamStanding) {
        return TeamStandingDto.builder()
                .countryId(countryId)
                .countryName(teamStanding.getCountryName())
                .leagueId(teamStanding.getLeagueId())
                .leagueName(teamStanding.getLeagueName())
                .teamId(teamStanding.getTeamId())
                .teamName(teamStanding.getTeamName())
                .overallLeaguePosition(teamStanding.getOverallLeaguePosition())
                .build();
    }

    private League getLeagueByName(final String leagueName, final String countryId) throws NotFoundException {
        final League[] leagues = leagueService.getLeaguesByCountryId(countryId, "get_leagues", API_KEY);
        return Arrays.stream(leagues).filter(leagueObj -> leagueObj.getLeagueName().equalsIgnoreCase(leagueName))
                .findFirst().orElseThrow(() -> new NotFoundException("a requested resource not found"));
    }

    private Map<String, String> getUriVariables(final String leagueId, final String action, final String apiKey) {
        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("league_id", leagueId);
        uriVariables.put(ACTION_HEADER, action);
        uriVariables.put(API_KEY_HEADER, apiKey);
        return uriVariables;
    }
}
