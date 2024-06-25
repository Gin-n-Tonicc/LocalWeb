package com.localWeb.localWeb.service;

import com.localWeb.localWeb.models.dto.request.GroupRequest;
import com.localWeb.localWeb.models.dto.response.GroupResponse;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    List<GroupResponse> getAllGroups();

    GroupResponse getGroupById(UUID id);

    GroupResponse createGroup(GroupRequest groupDTO);

    GroupResponse updateGroup(UUID id, GroupRequest groupDTO);

    void deleteGroup(UUID id);
}