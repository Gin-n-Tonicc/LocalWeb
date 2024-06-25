package com.localWeb.localWeb.service.impl;

import com.localWeb.localWeb.models.dto.request.CityRequest;
import com.localWeb.localWeb.models.dto.response.CityResponse;
import com.localWeb.localWeb.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CityServiceImpl implements CityService {

    @Override
    public List<CityResponse> getAllCities() {
        return List.of();
    }

    @Override
    public CityResponse getCityById(UUID id) {
        return null;
    }

    @Override
    public CityResponse createCity(CityRequest cityDTO) {
        return null;
    }

    @Override
    public CityResponse updateCity(UUID id, CityRequest cityDTO) {
        return null;
    }

    @Override
    public void deleteCity(UUID id) {

    }
}