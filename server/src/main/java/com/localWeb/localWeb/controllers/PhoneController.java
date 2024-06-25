package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.PhoneRequest;
import com.localWeb.localWeb.models.dto.response.PhoneResponse;
import com.localWeb.localWeb.service.PhoneService;
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
@RequestMapping("/api/v1/phones")
@Tag(name = "Phones")
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Operation(summary = "Get all phones", description = "Retrieves a list of all phones")
    @GetMapping
    public ResponseEntity<List<PhoneResponse>> getAllPhones() {
        return ResponseEntity.ok(phoneService.getAllPhones());
    }

    @Operation(summary = "Get phone by id", description = "Retrieves a phone by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneResponse.class)))
    @ApiResponse(responseCode = "404", description = "Phone not found")
    @GetMapping("/{id}")
    public ResponseEntity<PhoneResponse> getPhoneById(@PathVariable @Parameter(description = "Phone id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(phoneService.getPhoneById(id));
    }

    @Operation(summary = "Create a new phone", description = "Creates a new phone")
    @ApiResponse(responseCode = "201", description = "Phone created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneResponse.class)))
    @PostMapping
    public ResponseEntity<PhoneResponse> createPhone(@Valid @RequestBody PhoneRequest phoneRequest) {
        PhoneResponse createdPhone = phoneService.createPhone(phoneRequest);
        return new ResponseEntity<>(createdPhone, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a phone by id", description = "Updates an existing phone identified by its id")
    @ApiResponse(responseCode = "200", description = "Phone updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneResponse.class)))
    @ApiResponse(responseCode = "404", description = "Phone not found")
    @PutMapping("/{id}")
    public ResponseEntity<PhoneResponse> updatePhone(@PathVariable @Parameter(description = "Phone id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody PhoneRequest phoneRequest) {
        return ResponseEntity.ok(phoneService.updatePhone(id, phoneRequest));
    }

    @Operation(summary = "Delete a phone by id", description = "Deletes a phone identified by its id")
    @ApiResponse(responseCode = "200", description = "Phone deleted")
    @ApiResponse(responseCode = "404", description = "Phone not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhone(@PathVariable @Parameter(description = "Phone id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        phoneService.deletePhone(id);
        return ResponseEntity.ok("Phone with id: " + id + " has been deleted successfully!");
    }
}