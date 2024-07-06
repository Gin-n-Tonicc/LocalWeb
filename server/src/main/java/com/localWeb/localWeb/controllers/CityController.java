package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.CityRequest;
import com.localWeb.localWeb.models.dto.response.CityResponse;
import com.localWeb.localWeb.services.CityService;
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
@RequestMapping("/api/v1/cities")
@Tag(name = "Cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(summary = "Get all cities", description = "Retrieves a list of all cities")
    @GetMapping("/all")
    public ResponseEntity<List<CityResponse>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @Operation(summary = "Get city by id", description = "Retrieves a city by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CityResponse.class)))
    @ApiResponse(responseCode = "404", description = "City not found")
    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable @Parameter(description = "City id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @Operation(summary = "Create a new city", description = "Creates a new city")
    @ApiResponse(responseCode = "201", description = "City created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CityResponse.class)))
    @PostMapping("/create")
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest) {
        CityResponse createdCity = cityService.createCity(cityRequest);
        return new ResponseEntity<>(createdCity, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a city by id", description = "Updates an existing city identified by its id")
    @ApiResponse(responseCode = "200", description = "City updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CityResponse.class)))
    @ApiResponse(responseCode = "404", description = "City not found")
    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable @Parameter(description = "City id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody CityRequest cityRequest) {
        return ResponseEntity.ok(cityService.updateCity(id, cityRequest));
    }

    @Operation(summary = "Delete a city by id", description = "Deletes a city identified by its id")
    @ApiResponse(responseCode = "200", description = "City deleted")
    @ApiResponse(responseCode = "404", description = "City not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable @Parameter(description = "City id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok("City with id: " + id + " has been deleted successfully!");
    }
}
