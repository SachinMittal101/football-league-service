package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.entity.League;

public interface LeagueService {

    public League[] getLeaguesByCountryId(final String countryId, final String action, final String apiKey);
}
