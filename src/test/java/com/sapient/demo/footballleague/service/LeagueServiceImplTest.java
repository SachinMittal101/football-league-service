package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.entity.League;
import com.sapient.demo.footballleague.util.NotFoundException;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.sapient.demo.footballleague.TestData.*;
import static com.sapient.demo.footballleague.util.ServiceConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LeagueServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LeagueServiceImpl leagueService = new LeagueServiceImpl();

    @Test
    public void shouldTestRestTemplate() throws NotFoundException {
        final League league = new League(TEST_COUNTRY_ID, TEST_COUNTRY_NAME, TEST_LEAGUE_ID, TEST_LEAGUE_NAME);
        final League[] leagues = new League[1];
        leagues[0] = league;
        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("country_id", TEST_COUNTRY_ID);
        uriVariables.put(ACTION_HEADER, "get_leagues");
        uriVariables.put(API_KEY_HEADER, API_KEY);
        Mockito.when(restTemplate.getForEntity(BASE_URL + "?action={action}&country_id={country_id}&APIkey={APIkey}",
                League[].class, uriVariables))
                .thenReturn(new ResponseEntity(leagues, HttpStatus.OK));
        final League[] leaguesList = leagueService.getLeaguesByCountryId(TEST_COUNTRY_ID, "get_leagues", API_KEY);
        assertThat(leaguesList.length, Is.is(1));
        assertThat(leaguesList[0].getCountryId(), Is.is(TEST_COUNTRY_ID));
        assertThat(leaguesList[0].getCountryName(), Is.is(TEST_COUNTRY_NAME));
        assertThat(leaguesList[0].getLeagueId(), Is.is(TEST_LEAGUE_ID));
        assertThat(leaguesList[0].getLeagueName(), Is.is(TEST_LEAGUE_NAME));
    }
}