package com.sapient.demo.footballleague.service;

import com.sapient.demo.footballleague.entity.Country;
import com.sapient.demo.footballleague.util.NotFoundException;

public interface CountryService {

    public Country[] getCountries(final String action, final String apiKey);

    public Country getCountryByName(final String countryName) throws NotFoundException;
}
