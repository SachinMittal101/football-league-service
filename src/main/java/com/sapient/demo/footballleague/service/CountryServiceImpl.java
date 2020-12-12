package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.entity.Country;
import com.sapient.demo.footballleague.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.sapient.demo.footballleague.util.ServiceConstants.*;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private RestTemplate restTemplate;

    private static Map<String, String> getUriVariables(final String action, final String apiKey) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(ACTION_HEADER, action);
        uriVariables.put(API_KEY_HEADER, apiKey);
        return uriVariables;
    }

    @Override
    public Country[] getCountries(final String action, final String apiKey) {
        Map<String, String> uriVariables = getUriVariables(action, apiKey);
        return restTemplate.getForEntity(BASE_URL + "?action={action}&APIkey={APIkey}",
                Country[].class, uriVariables).getBody();
    }

    @Override
    public Country getCountryByName(final String countryName) throws NotFoundException {
        final Country[] countries = this.getCountries("get_countries", API_KEY);
        final Country country = Arrays.stream(countries).filter(countryObj -> countryObj.getCountryName()
                .equalsIgnoreCase(countryName)).findFirst()
                .orElseThrow(() -> new NotFoundException("a requested resource not found"));
        return country;
    }
}
