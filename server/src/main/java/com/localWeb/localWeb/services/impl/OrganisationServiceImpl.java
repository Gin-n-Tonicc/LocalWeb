package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.enums.Role;
import com.localWeb.localWeb.exceptions.common.AccessDeniedException;
import com.localWeb.localWeb.exceptions.files.FileNotFoundException;
import com.localWeb.localWeb.exceptions.organisation.OrganisationCreateException;
import com.localWeb.localWeb.exceptions.organisation.OrganisationNotFoundException;
import com.localWeb.localWeb.exceptions.user.UserNotFoundException;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.OrganisationRequestDTO;
import com.localWeb.localWeb.models.dto.response.OrganisationResponseDTO;
import com.localWeb.localWeb.models.entity.File;
import com.localWeb.localWeb.models.entity.Organisation;
import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.repositories.FileRepository;
import com.localWeb.localWeb.repositories.OrganisationRepository;
import com.localWeb.localWeb.repositories.UserRepository;
import com.localWeb.localWeb.services.OrganisationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final FileRepository fileRepository;

    @Override
    public OrganisationResponseDTO getOrganisationById(UUID id) {
        Organisation organisation = organisationRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new OrganisationNotFoundException(messageSource));
        return modelMapper.map(organisation, OrganisationResponseDTO.class);
    }
    
    @Override
    public OrganisationResponseDTO createOrganisation(OrganisationRequestDTO organisationRequestDTO, PublicUserDTO loggedUser) {
        Organisation organisation = modelMapper.map(organisationRequestDTO, Organisation.class);

        if (organisationRepository.findByEmail(organisation.getEmail()).isPresent()) {
            throw new OrganisationCreateException(messageSource, true);
        }

        User user = userRepository.findByEmail(loggedUser.getEmail()).orElseThrow(() -> new UserNotFoundException(messageSource));
        organisation.getOwners().add(user);

        if (!organisationRequestDTO.getFiles().isEmpty()) {
            Set<File> files = new HashSet<>();
            for (UUID fileId : organisationRequestDTO.getFiles()) {
                File file = fileRepository.findById(fileId)
                        .orElseThrow(() -> new FileNotFoundException(messageSource));
                files.add(file);
            }
            organisation.setFiles(files);
        }

        organisation = organisationRepository.save(organisation);

        return modelMapper.map(organisation, OrganisationResponseDTO.class);
    }

    @Override
    public OrganisationResponseDTO updateOrganisation(UUID id, OrganisationRequestDTO organisationDTO, PublicUserDTO loggedUser) {
        Organisation existingOrganisation = organisationRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new OrganisationNotFoundException(messageSource));

        User user = userRepository.findByEmail(loggedUser.getEmail())
                .orElseThrow(() -> new UserNotFoundException(messageSource));

        if (!(existingOrganisation.getOwners().contains(user)) && !(loggedUser.getRole().equals(Role.ADMIN))) {
            throw new AccessDeniedException(messageSource);
        }

        // Check if the new email already exists for another organisation
        if (organisationRepository.findByEmail(organisationDTO.getEmail())
                .filter(org -> !org.getId().equals(existingOrganisation.getId())) // Exclude current organisation
                .isPresent()) {
            throw new OrganisationCreateException(messageSource, true);
        }

        // Map fields from DTO to existing organisation entity
        existingOrganisation.setName(organisationDTO.getName());
        existingOrganisation.setDescription(organisationDTO.getDescription());
        existingOrganisation.setEmail(organisationDTO.getEmail());
        existingOrganisation.setWebsiteUrl(organisationDTO.getWebsiteUrl());

        // Handle profile image
        if (organisationDTO.getProfileImage() != null) {
            existingOrganisation.setProfileImage(fileRepository.findByIdAndDeletedAtIsNull(organisationDTO.getProfileImage()));
        }

        // Handle owners
        if (!organisationDTO.getOwners().isEmpty()) {
            Set<User> owners = new HashSet<>();
            for (UUID ownerId : organisationDTO.getOwners()) {
                User owner = userRepository.findById(ownerId)
                        .orElseThrow(() -> new UserNotFoundException(messageSource));
                owners.add(owner);
            }
            existingOrganisation.setOwners(owners);
        }

        // Handle files
        if (!organisationDTO.getFiles().isEmpty()) {
            Set<File> files = new HashSet<>();
            for (UUID fileId : organisationDTO.getFiles()) {
                File file = fileRepository.findById(fileId)
                        .orElseThrow(() -> new FileNotFoundException(messageSource));
                files.add(file);
            }
            existingOrganisation.setFiles(files);
        }

        System.out.println("FIRST " + existingOrganisation.getFiles().toString());
        Organisation updatedOrganisation = organisationRepository.save(existingOrganisation);
        System.out.println("SECOND " + updatedOrganisation.getFiles().toString());
        return modelMapper.map(updatedOrganisation, OrganisationResponseDTO.class);
    }
}