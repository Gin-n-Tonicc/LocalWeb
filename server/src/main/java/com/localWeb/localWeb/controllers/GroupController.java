package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.GroupRequest;
import com.localWeb.localWeb.models.dto.response.GroupResponse;
import com.localWeb.localWeb.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @Operation(summary = "Get group by id", description = "Retrieves a group by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupResponse.class)))
    @ApiResponse(responseCode = "404", description = "Group not found")
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable @Parameter(description = "Group id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @Operation(summary = "Create a new group", description = "Creates a new group")
    @ApiResponse(responseCode = "201", description = "Group created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupResponse.class)))
    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupRequest groupRequest) {
        GroupResponse createdGroup = groupService.createGroup(groupRequest);
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a group by id", description = "Updates an existing group identified by its id")
    @ApiResponse(responseCode = "200", description = "Group updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupResponse.class)))
    @ApiResponse(responseCode = "404", description = "Group not found")
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable @Parameter(description = "Group id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody GroupRequest groupRequest) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupRequest));
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