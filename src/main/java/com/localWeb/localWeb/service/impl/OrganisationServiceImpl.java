package com.localWeb.localWeb.service.impl;

import com.localWeb.localWeb.models.dto.request.OrganisationRequest;
import com.localWeb.localWeb.models.dto.response.OrganisationResponse;
import com.localWeb.localWeb.service.OrganisationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    @Override
    public List<OrganisationResponse> getAllOrganisations() {
        return List.of();
    }

    @Override
    public OrganisationResponse getOrganisationById(UUID id) {
        return null;
    }

    @Override
    public OrganisationResponse createOrganisation(OrganisationRequest organisationDTO) {
        return null;
    }

    @Override
    public OrganisationResponse updateOrganisation(UUID id, OrganisationRequest organisationDTO) {
        return null;
    }

    @Override
    public void deleteOrganisation(UUID id) {

    }
}