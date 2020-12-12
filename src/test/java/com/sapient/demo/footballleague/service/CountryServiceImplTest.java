package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.entity.Country;
import com.sapient.demo.footballleague.util.NotFoundException;
import org.hamcrest.MatcherAssert;
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

import static com.sapient.demo.footballleague.util.ServiceConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryService countryService = new CountryServiceImpl();

    @Test
    public void shouldTestRestTemplate() throws NotFoundException {
        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(ACTION_HEADER, "get_countries");
        uriVariables.put(API_KEY_HEADER, API_KEY);
        final Country country = new Country("test-country-id", "test-country-name");
        Country[] countries = new Country[1];
        countries[0] = country;
        Mockito.when(restTemplate.getForEntity(BASE_URL + "?action={action}&APIkey={APIkey}",
                Country[].class, uriVariables))
                .thenReturn(new ResponseEntity(countries, HttpStatus.OK));
        final Country result = countryService.getCountryByName("test-country-name");
        MatcherAssert.assertThat(result.getCountryId(), Is.is("test-country-id"));
        MatcherAssert.assertThat(result.getCountryName(), Is.is("test-country-name"));
    }
}