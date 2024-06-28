package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.request.PhoneRequest;
import com.localWeb.localWeb.models.dto.response.PhoneResponse;

import java.util.List;
import java.util.UUID;

public interface PhoneService {

    List<PhoneResponse> getAllPhones();

    PhoneResponse getPhoneById(UUID id);

    PhoneResponse createPhone(PhoneRequest phoneDTO);

    PhoneResponse updatePhone(UUID id, PhoneRequest phoneDTO);

    void deletePhone(UUID id);
}