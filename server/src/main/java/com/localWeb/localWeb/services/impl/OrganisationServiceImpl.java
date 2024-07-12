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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final FileRepository fileRepository;

    @Override
    public List<OrganisationResponseDTO> getAllOrganisations(PublicUserDTO loggedUser) {
        if(loggedUser!=null){
            if(loggedUser.getRole() == Role.ADMIN){
                List<Organisation> allOrganisations = organisationRepository.findAll();
                return allOrganisations.stream().map(organisation -> modelMapper.map(organisation, OrganisationResponseDTO.class)).toList();
            }
        }
        List<Organisation> allOrganisations = organisationRepository.findAllByDeletedAtIsNull();
        return allOrganisations.stream().map(organisation -> modelMapper.map(organisation, OrganisationResponseDTO.class)).toList();
    }

    @Override
    public OrganisationResponseDTO getOrganisationById(UUID id) {
        Organisation organisation = organisationRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new OrganisationNotFoundException(messageSource));
        return modelMapper.map(organisation, OrganisationResponseDTO.class);
    }

    @Override
    public OrganisationResponseDTO createOrganisation(OrganisationRequestDTO organisationRequestDTO, PublicUserDTO loggedUser) {
        Organisation organisation = mapOrganisationRequestToEntity(organisationRequestDTO);

        if (organisationRepository.findByEmail(organisation.getEmail()).isPresent()) {
            throw new OrganisationCreateException(messageSource, true);
        }

        if (!organisationRequestDTO.getOwners().isEmpty()) {
            Set<User> owners = findUsersByIds(organisationRequestDTO.getOwners());
            organisation.setOwners(owners);
            organisation.setMembers(owners); // The owners are also members
        }

        User user = findUserByEmail(loggedUser.getEmail());
        organisation.getOwners().add(user);
        organisation.getMembers().add(user);

        if (!organisationRequestDTO.getFiles().isEmpty()) {
            Set<File> files = findFilesByIds(organisationRequestDTO.getFiles());
            organisation.setFiles(files);
        }

        organisation.setProfileImage(fileRepository.findByIdAndDeletedAtIsNull(organisationRequestDTO.getProfileImage()));
        organisation = organisationRepository.save(organisation);

        return modelMapper.map(organisation, OrganisationResponseDTO.class);
    }

    @Override
    public OrganisationResponseDTO updateOrganisation(UUID id, OrganisationRequestDTO organisationDTO, PublicUserDTO loggedUser) {
        Organisation existingOrganisation = organisationRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new OrganisationNotFoundException(messageSource));

        User user = findUserByEmail(loggedUser.getEmail());

        if (!(existingOrganisation.getOwners().contains(user)) && !(loggedUser.getRole().equals(Role.ADMIN))) {
            throw new AccessDeniedException(messageSource);
        }

        Organisation finalExistingOrganisation = existingOrganisation;
        if (organisationRepository.findByEmail(organisationDTO.getEmail())
                .filter(org -> !org.getId().equals(finalExistingOrganisation.getId())) // Exclude current organisation
                .isPresent()) {
            throw new OrganisationCreateException(messageSource, true);
        }

        existingOrganisation = mapOrganisationRequestToEntity(existingOrganisation, organisationDTO);

        if (!organisationDTO.getOwners().isEmpty()) {
            Set<User> owners = findUsersByIds(organisationDTO.getOwners());
            existingOrganisation.setOwners(owners);
        }

        if (!organisationDTO.getFiles().isEmpty()) {
            Set<File> files = findFilesByIds(organisationDTO.getFiles());
            existingOrganisation.setFiles(files);
        }

        Organisation updatedOrganisation = organisationRepository.save(existingOrganisation);
        return modelMapper.map(updatedOrganisation, OrganisationResponseDTO.class);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(messageSource));
    }

    private Set<User> findUsersByIds(Set<UUID> userIds) {
        Set<User> users = new HashSet<>();

        for (UUID userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(messageSource));
            users.add(user);
        }
        return users;
    }

    private Set<File> findFilesByIds(Set<UUID> fileIds) {
        Set<File> files = new HashSet<>();

        for (UUID fileId : fileIds) {
            File file = fileRepository.findById(fileId)
                    .orElseThrow(() -> new FileNotFoundException(messageSource));
            files.add(file);
        }
        return files;
    }

    private Organisation mapOrganisationRequestToEntity(OrganisationRequestDTO organisationRequestDTO) {
        Organisation organisation = modelMapper.map(organisationRequestDTO, Organisation.class);

        if (organisationRequestDTO.getProfileImage() != null) {
            organisation.setProfileImage(fileRepository.findByIdAndDeletedAtIsNull(organisationRequestDTO.getProfileImage()));
        }

        return organisation;
    }

    private Organisation mapOrganisationRequestToEntity(Organisation existingOrganisation, OrganisationRequestDTO organisationDTO) {
        existingOrganisation.setName(organisationDTO.getName());
        existingOrganisation.setDescription(organisationDTO.getDescription());
        existingOrganisation.setEmail(organisationDTO.getEmail());
        existingOrganisation.setWebsiteUrl(organisationDTO.getWebsiteUrl());

        if (organisationDTO.getProfileImage() != null) {
            existingOrganisation.setProfileImage(fileRepository.findByIdAndDeletedAtIsNull(organisationDTO.getProfileImage()));
        }

        return existingOrganisation;
    }
}