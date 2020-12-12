package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.entity.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.sapient.demo.footballleague.util.ServiceConstants.*;

@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public League[] getLeaguesByCountryId(String countryId, String action, String apiKey) {
        Map<String, String> uriVariables = getUriVariables(countryId, action, apiKey);
        return restTemplate.getForEntity(BASE_URL + "?action={action}&country_id={country_id}&APIkey={APIkey}",
                League[].class, uriVariables).getBody();
    }

    private Map<String, String> getUriVariables(final String countryId, final String action, final String apiKey) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("country_id", countryId);
        uriVariables.put(ACTION_HEADER, action);
        uriVariables.put(API_KEY_HEADER, apiKey);
        return uriVariables;
    }
}
