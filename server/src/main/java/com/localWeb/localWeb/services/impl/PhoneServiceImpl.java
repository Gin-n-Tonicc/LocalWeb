package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.models.dto.request.PhoneRequest;
import com.localWeb.localWeb.models.dto.response.PhoneResponse;
import com.localWeb.localWeb.services.PhoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Override
    public List<PhoneResponse> getAllPhones() {
        return List.of();
    }

    @Override
    public PhoneResponse getPhoneById(UUID id) {
        return null;
    }

    @Override
    public PhoneResponse createPhone(PhoneRequest phoneDTO) {
        return null;
    }

    @Override
    public PhoneResponse updatePhone(UUID id, PhoneRequest phoneDTO) {
        return null;
    }

    @Override
    public void deletePhone(UUID id) {

    }
}