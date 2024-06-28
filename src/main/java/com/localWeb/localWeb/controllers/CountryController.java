package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.common.CountryDTO;
import com.localWeb.localWeb.services.CountryService;
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
@RequestMapping("/api/v1/countries")
@Tag(name = "Countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @Operation(summary = "Get all countries", description = "Retrieves a list of all countries")
    @GetMapping("/all")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @Operation(summary = "Get country by id", description = "Retrieves a country by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Country not found")
    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable @Parameter(description = "Country id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @Operation(summary = "Create a new country", description = "Creates a new country")
    @ApiResponse(responseCode = "201", description = "Country created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDTO.class)))
    @PostMapping("/create")
    public ResponseEntity<CountryDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
        CountryDTO createdCountry = countryService.createCountry(countryDTO);
        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a country by id", description = "Updates an existing country identified by its id")
    @ApiResponse(responseCode = "200", description = "Country updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Country not found")
    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable @Parameter(description = "Country id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryService.updateCountry(id, countryDTO));
    }

    @Operation(summary = "Delete a country by id", description = "Deletes a country identified by its id")
    @ApiResponse(responseCode = "200", description = "Country deleted")
    @ApiResponse(responseCode = "404", description = "Country not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountryById(@PathVariable @Parameter(description = "Country id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok("Country with id: " + id + " has been deleted successfully!");
    }
}