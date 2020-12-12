package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.TestData;
import com.sapient.demo.footballleague.dto.TeamStandingDto;
import com.sapient.demo.footballleague.entity.Country;
import com.sapient.demo.footballleague.entity.League;
import com.sapient.demo.footballleague.entity.TeamStanding;
import com.sapient.demo.footballleague.util.NotFoundException;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.sapient.demo.footballleague.TestData.*;
import static com.sapient.demo.footballleague.util.ServiceConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamStandingServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CountryService countryService = new CountryServiceImpl();

    @Mock
    private LeagueService leagueService = new LeagueServiceImpl();

    @InjectMocks
    private TeamStandingService teamStandingService = new TeamStandingServiceImpl();

    @Test
    public void shouldGetTeamStandingDetails() throws NotFoundException {
        final Map<String, String> uriVariablesForCountry = new HashMap<>();
        uriVariablesForCountry.put(ACTION_HEADER, "get_countries");
        uriVariablesForCountry.put(API_KEY_HEADER, API_KEY);
        final Country country = new Country(TEST_COUNTRY_ID, TEST_COUNTRY_NAME);
        Country[] countries = new Country[1];
        countries[0] = country;
        lenient().when(restTemplate.getForEntity(BASE_URL + "?action={action}&APIkey={APIkey}",
                Country[].class, uriVariablesForCountry))
                .thenReturn(new ResponseEntity(countries, HttpStatus.OK));
        final League league = new League(TEST_COUNTRY_ID, TEST_COUNTRY_NAME, TEST_LEAGUE_ID, TEST_LEAGUE_NAME);
        final League[] leagues = new League[1];
        leagues[0] = league;

        final Map<String, String> uriVariablesForLeague = new HashMap<>();
        uriVariablesForLeague.put(ACTION_HEADER, "get_leagues");
        uriVariablesForLeague.put(API_KEY_HEADER, API_KEY);
        uriVariablesForLeague.put("country_id", TEST_COUNTRY_ID);

        lenient().when(restTemplate.getForEntity(BASE_URL + "?action={action}&country_id={country_id}&APIkey={APIkey}",
                League[].class, uriVariablesForLeague))
                .thenReturn(new ResponseEntity(leagues, HttpStatus.OK));
        doReturn(country).when(countryService).getCountryByName(TEST_COUNTRY_NAME);
        doReturn(leagues).when(leagueService).getLeaguesByCountryId(TEST_COUNTRY_ID, "get_leagues", API_KEY);

        final Map<String, String> uriVariablesForStandings = new HashMap<>();
        uriVariablesForStandings.put(ACTION_HEADER, "get_standings");
        uriVariablesForStandings.put(API_KEY_HEADER, API_KEY);
        uriVariablesForStandings.put("league_id", TEST_LEAGUE_ID);
        final TeamStanding teamStanding = new TeamStanding(TEST_COUNTRY_ID, TEST_COUNTRY_NAME, TEST_LEAGUE_ID, TEST_LEAGUE_NAME, TEST_TEAM_ID,
                TEST_TEAM_NAME, "1");
        final TeamStanding[] teamStandings = new TeamStanding[1];
        teamStandings[0] = teamStanding;

        lenient().when(restTemplate.getForEntity(BASE_URL + "?action={action}&league_id={league_id}&APIkey={APIkey}",
                TeamStanding[].class, uriVariablesForStandings))
                .thenReturn(new ResponseEntity(teamStandings, HttpStatus.OK));

        final TeamStandingDto teamStandingDto = teamStandingService.getTeamStandingDetails(TEST_COUNTRY_NAME, TEST_LEAGUE_NAME, TEST_TEAM_NAME);
        assertThat(teamStandingDto.getCountryId(), Is.is(TEST_COUNTRY_ID));
        assertThat(teamStandingDto.getCountryName(), Is.is(TEST_COUNTRY_NAME));
        assertThat(teamStandingDto.getLeagueId(), Is.is(TEST_LEAGUE_ID));
        assertThat(teamStandingDto.getLeagueName(), Is.is(TEST_LEAGUE_NAME));
        assertThat(teamStandingDto.getTeamId(), Is.is(TEST_TEAM_ID));
        assertThat(teamStandingDto.getTeamName(), Is.is(TEST_TEAM_NAME));
        assertThat(teamStandingDto.getOverallLeaguePosition(), Is.is("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() throws NotFoundException {
        teamStandingService.getTeamStandingDetails("", TestData.TEST_LEAGUE_NAME, TestData.TEST_TEAM_NAME);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenCountryNotFound() throws NotFoundException {
        when(countryService.getCountryByName(TEST_COUNTRY_NAME)).thenThrow(new NotFoundException("a requested resource not found"));
        teamStandingService.getTeamStandingDetails(TEST_COUNTRY_NAME, TestData.TEST_LEAGUE_NAME, TestData.TEST_TEAM_NAME);
    }
}