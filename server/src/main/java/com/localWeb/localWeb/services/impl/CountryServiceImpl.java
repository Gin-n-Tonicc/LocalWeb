package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.exceptions.country.CountryCreateException;
import com.localWeb.localWeb.exceptions.country.CountryNotFoundException;
import com.localWeb.localWeb.models.dto.common.CountryDTO;
import com.localWeb.localWeb.models.entity.Country;
import com.localWeb.localWeb.repositories.CountryRepository;
import com.localWeb.localWeb.services.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, MessageSource messageSource) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    @Override
    public List<CountryDTO> getAllCountries() {
        List<Country> countries = countryRepository.findAllByDeletedAtIsNull();
        return countries.stream().map(country -> modelMapper.map(country, CountryDTO.class)).toList();
    }

    @Override
    public CountryDTO getCountryById(UUID id) {
        Optional<Country> country = countryRepository.findByIdAndDeletedAtIsNull(id);
        if (country.isPresent()) {
            return modelMapper.map(country.get(), CountryDTO.class);
        }
        throw new CountryNotFoundException(messageSource);
    }

    @Override
    public CountryDTO createCountry(CountryDTO countryDTO) {
        try {
            Country countryEntity = countryRepository.save(modelMapper.map(countryDTO, Country.class));
            return modelMapper.map(countryEntity, CountryDTO.class);
        } catch (DataIntegrityViolationException exception) {
            // If a country with the same name already exists
            throw new CountryCreateException(messageSource, true);
        }
    }

    @Override
    public CountryDTO updateCountry(UUID id, CountryDTO countryDTO) {
        Optional<Country> existingCountryOptional = countryRepository.findByIdAndDeletedAtIsNull(id);

        if (existingCountryOptional.isEmpty()) {
            throw new CountryNotFoundException(messageSource);
        }

        Country existingCountry = existingCountryOptional.get();
        modelMapper.map(countryDTO, existingCountry);

        existingCountry.setId(id);
        Country updatedCountry = countryRepository.save(existingCountry);
        return modelMapper.map(updatedCountry, CountryDTO.class);
    }

    @Override
    public void deleteCountry(UUID id) {
        Optional<Country> country = countryRepository.findByIdAndDeletedAtIsNull(id);
        if (country.isPresent()) {
            // Soft delete
            country.get().setDeletedAt(LocalDateTime.now());
            countryRepository.save(country.get());
        } else {
            throw new CountryNotFoundException(messageSource);
        }
    }
}

