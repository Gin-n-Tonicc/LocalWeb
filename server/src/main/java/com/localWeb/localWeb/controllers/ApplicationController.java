package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.enums.ApplicationStatus;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.ApplicationRequestDTO;
import com.localWeb.localWeb.models.dto.response.ApplicationResponseDTO;
import com.localWeb.localWeb.models.dto.response.LessonResponseDTO;
import com.localWeb.localWeb.security.filters.JwtAuthenticationFilter;
import com.localWeb.localWeb.services.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
@Tag(name = "Applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "Create a new application", description = "Creates a new application")
    @ApiResponse(responseCode = "201", description = "Applied!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationResponseDTO.class)))
    @PostMapping("/apply")
    public ResponseEntity<ApplicationResponseDTO> applyForLesson(@RequestBody ApplicationRequestDTO applicationRequestDTO, HttpServletRequest httpServletRequest) {
        ApplicationResponseDTO application = applicationService.applyForLesson((PublicUserDTO) httpServletRequest.getAttribute(JwtAuthenticationFilter.userKey), applicationRequestDTO);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all pending applications by lesson id", description = "Retrieves all the pending application for a lesson")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponseDTO.class)))
    @GetMapping("/pending")
    public ResponseEntity<List<ApplicationResponseDTO>> getPendingApplications(@RequestParam UUID lessonId) {
        List<ApplicationResponseDTO> applications = applicationService.getPendingApplications(lessonId);
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "Update the status of an application", description = "Allows organization owners or admins to update the status of a user's application to join a lesson." + "Based on who views the application we expect to enroll the user in their group (in case the user has more than 1 group we give the group id).")
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateApplicationStatus(@PathVariable UUID id, @RequestParam ApplicationStatus status, @RequestParam UUID groupId, HttpServletRequest httpServletRequest) {
        ApplicationResponseDTO application = applicationService.updateApplicationStatus(id, status, groupId, (PublicUserDTO) httpServletRequest.getAttribute(JwtAuthenticationFilter.userKey));
        return ResponseEntity.ok(application);
    }
}