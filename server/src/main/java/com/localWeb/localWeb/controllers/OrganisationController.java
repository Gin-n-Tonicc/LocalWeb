package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.OrganisationRequest;
import com.localWeb.localWeb.models.dto.response.OrganisationResponse;
import com.localWeb.localWeb.services.OrganisationService;
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
@RequestMapping("/api/v1/organisations")
@Tag(name = "Organisations")
public class OrganisationController {

    private final OrganisationService organisationService;

    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @Operation(summary = "Get all organisations", description = "Retrieves a list of all organisations")
    @GetMapping
    public ResponseEntity<List<OrganisationResponse>> getAllOrganisations() {
        return ResponseEntity.ok(organisationService.getAllOrganisations());
    }

    @Operation(summary = "Get organisation by id", description = "Retrieves an organisation by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrganisationResponse.class)))
    @ApiResponse(responseCode = "404", description = "Organisation not found")
    @GetMapping("/{id}")
    public ResponseEntity<OrganisationResponse> getOrganisationById(@PathVariable @Parameter(description = "Organisation id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(organisationService.getOrganisationById(id));
    }

    @Operation(summary = "Create a new organisation", description = "Creates a new organisation")
    @ApiResponse(responseCode = "201", description = "Organisation created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrganisationResponse.class)))
    @PostMapping
    public ResponseEntity<OrganisationResponse> createOrganisation(@Valid @RequestBody OrganisationRequest organisationRequest) {
        OrganisationResponse createdOrganisation = organisationService.createOrganisation(organisationRequest);
        return new ResponseEntity<>(createdOrganisation, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an organisation by id", description = "Updates an existing organisation identified by its id")
    @ApiResponse(responseCode = "200", description = "Organisation updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrganisationResponse.class)))
    @ApiResponse(responseCode = "404", description = "Organisation not found")
    @PutMapping("/{id}")
    public ResponseEntity<OrganisationResponse> updateOrganisation(@PathVariable @Parameter(description = "Organisation id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody OrganisationRequest organisationRequest) {
        return ResponseEntity.ok(organisationService.updateOrganisation(id, organisationRequest));
    }

    @Operation(summary = "Delete an organisation by id", description = "Deletes an organisation identified by its id")
    @ApiResponse(responseCode = "200", description = "Organisation deleted")
    @ApiResponse(responseCode = "404", description = "Organisation not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganisation(@PathVariable @Parameter(description = "Organisation id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        organisationService.deleteOrganisation(id);
        return ResponseEntity.ok("Organisation with id: " + id + " has been deleted successfully!");
    }
}