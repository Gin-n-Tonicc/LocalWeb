package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.OrganisationRequestDTO;
import com.localWeb.localWeb.models.dto.response.OrganisationResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrganisationService {

    OrganisationResponseDTO createOrganisation(OrganisationRequestDTO courseDTO, PublicUserDTO loggedUser);

    OrganisationResponseDTO updateOrganisation(UUID id, OrganisationRequestDTO organisationDTO, PublicUserDTO loggedUser);

    OrganisationResponseDTO getOrganisationById(UUID id);

    List<OrganisationResponseDTO> getAllOrganisations(PublicUserDTO loggedUser);

    void deleteOrganisation(UUID id, PublicUserDTO loggedUser);

    OrganisationResponseDTO addMemberToOrganisation(UUID organisationId, PublicUserDTO loggedUser);

    OrganisationResponseDTO removeMemberFromOrganisation(UUID organisationId, PublicUserDTO loggedUser);

    Set<PublicUserDTO> listMembersOfOrganisation(UUID organisationId);
}