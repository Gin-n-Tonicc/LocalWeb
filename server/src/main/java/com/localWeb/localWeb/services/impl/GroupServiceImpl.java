package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.enums.Role;
import com.localWeb.localWeb.exceptions.common.AccessDeniedException;
import com.localWeb.localWeb.exceptions.group.GroupNotFoundException;
import com.localWeb.localWeb.exceptions.lesson.LessonNotFoundException;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.GroupRequestDTO;
import com.localWeb.localWeb.models.dto.response.GroupResponseDTO;
import com.localWeb.localWeb.models.entity.Group;
import com.localWeb.localWeb.models.entity.Lesson;
import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.repositories.GroupRepository;
import com.localWeb.localWeb.repositories.LessonRepository;
import com.localWeb.localWeb.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final LessonRepository lessonRepository;
    private final MessageSource messageSource;

    @Override
    public List<GroupResponseDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAllByDeletedAtIsNull();
        return groups.stream().map(group -> modelMapper.map(group, GroupResponseDTO.class)).toList();
    }

    @Override
    public List<GroupResponseDTO> getAllByLesson(UUID id) {
        List<Group> groups = groupRepository.findAllByLessonIdAndDeletedAtIsNull(id);
        return groups.stream().map(group -> modelMapper.map(group, GroupResponseDTO.class)).toList();
    }

    @Override
    public GroupResponseDTO getGroupById(UUID id) {
        Optional<Group> group = groupRepository.findByIdAndDeletedAtIsNull(id);
        if (group.isPresent()) {
            return modelMapper.map(group.get(), GroupResponseDTO.class);
        }
        throw new GroupNotFoundException(messageSource);
    }

    @Override
    public GroupResponseDTO createGroup(GroupRequestDTO groupDTO, PublicUserDTO loggedUser) {
        Lesson lesson = lessonRepository.findByIdAndDeletedAtIsNull(groupDTO.getLesson()).orElseThrow(() -> new LessonNotFoundException(messageSource));

        User owner = modelMapper.map(loggedUser, User.class);

        if (!(lesson.getOrganisation().getOwners().contains(owner)) && !(loggedUser.getRole().equals(Role.ADMIN))) {
            throw new AccessDeniedException(messageSource);
        }

        Group groupEntity = modelMapper.map(groupDTO, Group.class);
        groupEntity.setLesson(lesson);
        groupEntity.setOwner(owner);
        groupEntity = groupRepository.save(groupEntity);

        return modelMapper.map(groupEntity, GroupResponseDTO.class);
    }

    @Override
    public GroupResponseDTO updateGroup(UUID id, GroupRequestDTO groupDTO, PublicUserDTO loggedUser) {
        Group existingGroup = groupRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new GroupNotFoundException(messageSource));

        Lesson lesson = lessonRepository.findByIdAndDeletedAtIsNull(groupDTO.getLesson()).orElseThrow(() -> new LessonNotFoundException(messageSource));

        User owner = modelMapper.map(loggedUser, User.class);

        if (!(lesson.getOrganisation().getOwners().contains(owner)) && !(loggedUser.getRole().equals(Role.ADMIN))) {
            throw new AccessDeniedException(messageSource);
        }

        modelMapper.map(groupDTO, existingGroup);

        existingGroup.setLesson(lesson);

        Group updatedGroup = groupRepository.save(existingGroup);

        return modelMapper.map(updatedGroup, GroupResponseDTO.class);
    }

    @Override
    public void deleteGroup(UUID id) {
        Optional<Group> group = groupRepository.findByIdAndDeletedAtIsNull(id);
        if (group.isPresent()) {
            group.get().setDeletedAt(LocalDateTime.now());
            groupRepository.save(group.get());
        } else {
            throw new GroupNotFoundException(messageSource);
        }
    }
}