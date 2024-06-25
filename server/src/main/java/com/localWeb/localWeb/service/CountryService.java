package com.localWeb.localWeb.service;

import com.localWeb.localWeb.models.dto.common.CountryDTO;
import java.util.List;
import java.util.UUID;

public interface CountryService {

    List<CountryDTO> getAllCountries();

    CountryDTO getCountryById(UUID id);

    CountryDTO createCountry(CountryDTO countryDTO);

    CountryDTO updateCountry(UUID id, CountryDTO countryDTO);

    void deleteCountry(UUID id);
}

