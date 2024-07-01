package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.request.CityRequest;
import com.localWeb.localWeb.models.dto.response.CityResponse;

import java.util.List;
import java.util.UUID;

public interface CityService {

    List<CityResponse> getAllCities();

    CityResponse getCityById(UUID id);

    CityResponse createCity(CityRequest cityDTO);

    CityResponse updateCity(UUID id, CityRequest cityDTO);

    void deleteCity(UUID id);
}
