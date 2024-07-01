package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.models.dto.common.CountryDTO;
import com.localWeb.localWeb.services.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CountryServiceImpl implements CountryService {

    @Override
    public List<CountryDTO> getAllCountries() {
        return List.of();
    }

    @Override
    public CountryDTO getCountryById(UUID id) {
        return null;
    }

    @Override
    public CountryDTO createCountry(CountryDTO countryDTO) {
        return null;
    }

    @Override
    public CountryDTO updateCountry(UUID id, CountryDTO countryDTO) {
        return null;
    }

    @Override
    public void deleteCountry(UUID id) {

    }
}

