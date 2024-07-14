package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.GroupRequestDTO;
import com.localWeb.localWeb.models.dto.response.GroupResponseDTO;
import com.localWeb.localWeb.models.dto.response.LessonResponseDTO;
import com.localWeb.localWeb.security.filters.JwtAuthenticationFilter;
import com.localWeb.localWeb.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
@Tag(name = "Groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "Get all groups", description = "Retrieves a list of all groups")
    @GetMapping("/all")
    public ResponseEntity<List<GroupResponseDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @Operation(summary = "Get group by id", description = "Retrieves a group by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Group not found")
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable @Parameter(description = "Group id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @Operation(summary = "Create a new group", description = "Creates a new group")
    @ApiResponse(responseCode = "201", description = "Group created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupResponseDTO.class)))
    @PostMapping("/create")
    public ResponseEntity<GroupResponseDTO> createGroup(@Valid @RequestBody GroupRequestDTO GroupRequestDTO, HttpServletRequest httpServletRequest) {
        GroupResponseDTO createdGroup = groupService.createGroup(GroupRequestDTO, (PublicUserDTO) httpServletRequest.getAttribute(JwtAuthenticationFilter.userKey));
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all groups by lesson id", description = "Retrieves all the groups in a lesson")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Lesson not found")
    @GetMapping("/lesson/{id}")
    public ResponseEntity<List<GroupResponseDTO>> getAllByLesson(@PathVariable @Parameter(description = "Lesson id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(groupService.getAllByLesson(id));
    }


    @Operation(summary = "Update a group by id", description = "Updates an existing group identified by its id")
    @ApiResponse(responseCode = "200", description = "Group updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Group not found")
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(@PathVariable @Parameter(description = "Group id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody GroupRequestDTO GroupRequestDTO, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(groupService.updateGroup(id, GroupRequestDTO, (PublicUserDTO) httpServletRequest.getAttribute(JwtAuthenticationFilter.userKey)));
    }

    @Operation(summary = "Delete a group by id", description = "Deletes a group identified by its id")
    @ApiResponse(responseCode = "200", description = "Group deleted")
    @ApiResponse(responseCode = "404", description = "Group not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable @Parameter(description = "Group id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok("Group with id: " + id + " has been deleted successfully!");
    }
}