package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.OrganisationRequestDTO;
import com.localWeb.localWeb.models.dto.response.OrganisationResponseDTO;

import java.util.UUID;

public interface OrganisationService {

    OrganisationResponseDTO createOrganisation(OrganisationRequestDTO courseDTO, PublicUserDTO loggedUser);

    OrganisationResponseDTO updateOrganisation(UUID id, OrganisationRequestDTO organisationDTO, PublicUserDTO loggedUser);
    OrganisationResponseDTO getOrganisationById(UUID id);

}