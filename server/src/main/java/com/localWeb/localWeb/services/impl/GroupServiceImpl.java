package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.models.dto.request.GroupRequest;
import com.localWeb.localWeb.models.dto.response.GroupResponse;
import com.localWeb.localWeb.services.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Override
    public List<GroupResponse> getAllGroups() {
        return List.of();
    }

    @Override
    public GroupResponse getGroupById(UUID id) {
        return null;
    }

    @Override
    public GroupResponse createGroup(GroupRequest groupDTO) {
        return null;
    }

    @Override
    public GroupResponse updateGroup(UUID id, GroupRequest groupDTO) {
        return null;
    }

    @Override
    public void deleteGroup(UUID id) {

    }
}
