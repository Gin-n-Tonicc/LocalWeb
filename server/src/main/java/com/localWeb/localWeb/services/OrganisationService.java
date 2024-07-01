package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.request.OrganisationRequest;
import com.localWeb.localWeb.models.dto.response.OrganisationResponse;

import java.util.List;
import java.util.UUID;

public interface OrganisationService {

    List<OrganisationResponse> getAllOrganisations();

    OrganisationResponse getOrganisationById(UUID id);

    OrganisationResponse createOrganisation(OrganisationRequest organisationDTO);

    OrganisationResponse updateOrganisation(UUID id, OrganisationRequest organisationDTO);

    void deleteOrganisation(UUID id);
}