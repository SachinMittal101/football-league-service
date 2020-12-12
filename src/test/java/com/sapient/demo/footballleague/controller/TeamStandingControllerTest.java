package com.sapient.demo.footballleague.controller;

import com.sapient.demo.footballleague.dto.TeamStandingDto;
import com.sapient.demo.footballleague.service.TeamStandingService;
import com.sapient.demo.footballleague.util.NotFoundException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sapient.demo.footballleague.TestData.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TeamStandingController.class)
public class TeamStandingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamStandingService teamStandingService;

    @Test
    public void shouldReturnForGetTeamStanding() throws Exception {
        when(teamStandingService.getTeamStandingDetails(TEST_COUNTRY_NAME, TEST_LEAGUE_NAME, TEST_TEAM_NAME))
                .thenReturn(TeamStandingDto.builder().countryId(TEST_COUNTRY_ID).build());
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/?country_name=england&league_name=Championship&team_name=norwich");
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        final MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(HttpStatus.OK.value(), Is.is(response.getStatus()));
    }

    @Test
    public void shouldReturn400ForEmptyTeamName() throws Exception {
        when(teamStandingService.getTeamStandingDetails(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new IllegalArgumentException());
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/?country_name=england&league_name=Championship&team_name=");
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        final MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(HttpStatus.BAD_REQUEST.value(), Is.is(response.getStatus()));
    }

    @Test
    public void shouldReturn404WhenCountryNameIsNotFound() throws Exception {
        when(teamStandingService.getTeamStandingDetails(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new NotFoundException("a requested resource not found"));
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/?country_name=test-abc&league_name=Championship&team_name=");
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        final MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(HttpStatus.NOT_FOUND.value(), Is.is(response.getStatus()));
    }
}