package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.EventRequest;
import com.localWeb.localWeb.models.dto.response.EventResponse;
import com.localWeb.localWeb.services.EventService;
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
@RequestMapping("/api/v1/events")
@Tag(name = "Events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Get all events", description = "Retrieves a list of all events")
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @Operation(summary = "Get event by id", description = "Retrieves an event by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
    @ApiResponse(responseCode = "404", description = "Event not found")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable @Parameter(description = "Event id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @Operation(summary = "Create a new event", description = "Creates a new event")
    @ApiResponse(responseCode = "201", description = "Event created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        EventResponse createdEvent = eventService.createEvent(eventRequest);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an event by id", description = "Updates an existing event identified by its id")
    @ApiResponse(responseCode = "200", description = "Event updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
    @ApiResponse(responseCode = "404", description = "Event not found")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable @Parameter(description = "Event id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest));
    }

    @Operation(summary = "Delete an event by id", description = "Deletes an event identified by its id")
    @ApiResponse(responseCode = "200", description = "Event deleted")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable @Parameter(description = "Event id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event with id: " + id + " has been deleted successfully!");
    }
}