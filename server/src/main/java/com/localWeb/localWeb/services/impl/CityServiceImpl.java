package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.exceptions.city.CityNotFoundException;
import com.localWeb.localWeb.exceptions.country.CountryNotFoundException;
import com.localWeb.localWeb.models.dto.request.CityRequest;
import com.localWeb.localWeb.models.dto.response.CityResponse;
import com.localWeb.localWeb.models.entity.City;
import com.localWeb.localWeb.models.entity.Country;
import com.localWeb.localWeb.repositories.CityRepository;
import com.localWeb.localWeb.repositories.CountryRepository;
import com.localWeb.localWeb.services.CityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Override
    public List<CityResponse> getAllCities() {
        List<City> countries = cityRepository.findAllByDeletedAtIsNull();
        return countries.stream().map(city -> modelMapper.map(city, CityResponse.class)).toList();
    }

    @Override
    public CityResponse getCityById(UUID id) {
        Optional<City> city = cityRepository.findByIdAndDeletedAtIsNull(id);
        if (city.isPresent()) {
            return modelMapper.map(city.get(), CityResponse.class);
        }

        throw new CityNotFoundException(messageSource);
    }

    @Override
    public CityResponse createCity(CityRequest cityRequest) {
        Country country = countryRepository.findByIdAndDeletedAtIsNull(cityRequest.getCountry()).orElseThrow(() -> new CountryNotFoundException(messageSource));

        City cityEntity = modelMapper.map(cityRequest, City.class);
        cityEntity.setCountry(country);

        cityRepository.save(cityEntity);
        return modelMapper.map(cityEntity, CityResponse.class);
    }


    @Override
    public CityResponse updateCity(UUID id, CityRequest cityRequest) {
        Optional<City> existingCityOptional = cityRepository.findByIdAndDeletedAtIsNull(id);

        if (existingCityOptional.isEmpty()) {
            throw new CityNotFoundException(messageSource);
        }

        City existingCity = existingCityOptional.get();
        modelMapper.map(cityRequest, existingCity);

        existingCity.setId(id);
        City updatedCity = cityRepository.save(existingCity);
        return modelMapper.map(updatedCity, CityResponse.class);
    }

    @Override
    public void deleteCity(UUID id) {
        Optional<City> city = cityRepository.findByIdAndDeletedAtIsNull(id);
        if (city.isPresent()) {
            // Soft delete
            city.get().setDeletedAt(LocalDateTime.now());
            cityRepository.save(city.get());
        } else {
            throw new CityNotFoundException(messageSource);
        }
    }
}