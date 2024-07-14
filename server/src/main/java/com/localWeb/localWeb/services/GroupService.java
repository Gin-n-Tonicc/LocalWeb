package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.GroupRequestDTO;
import com.localWeb.localWeb.models.dto.response.GroupResponseDTO;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    List<GroupResponseDTO> getAllGroups();

    GroupResponseDTO getGroupById(UUID id);

    GroupResponseDTO createGroup(GroupRequestDTO groupDTO, PublicUserDTO loggedUser);

    GroupResponseDTO updateGroup(UUID id, GroupRequestDTO groupDTO, PublicUserDTO loggedUser);

    void deleteGroup(UUID id);

    List<GroupResponseDTO> getAllByLesson(UUID id);
}