package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.LessonRequestDTO;
import com.localWeb.localWeb.models.dto.response.LessonResponseDTO;
import com.localWeb.localWeb.services.LessonService;
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
@RequestMapping("/api/v1/lessons")
@Tag(name = "Lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Operation(summary = "Get all lessons", description = "Retrieves a list of all lessons")
    @GetMapping("/all")
    public ResponseEntity<List<LessonResponseDTO>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @Operation(summary = "Get lesson by id", description = "Retrieves a lesson by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Lesson not found")
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDTO> getLessonById(@PathVariable @Parameter(description = "Lesson id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @Operation(summary = "Get all lessons by organisation id", description = "Retrieves all the lesson in a organisation")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Organisation not found")
    @GetMapping("/{id}")
    public ResponseEntity<List<LessonResponseDTO>> getAllByOrganisation(@PathVariable @Parameter(description = "Lesson id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(lessonService.getAllByOrganisation(id));
    }

    @Operation(summary = "Create a new lesson", description = "Creates a new lesson")
    @ApiResponse(responseCode = "201", description = "Lesson created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponseDTO.class)))
    @PostMapping("/create")
    public ResponseEntity<LessonResponseDTO> createLesson(@Valid @RequestBody LessonRequestDTO lessonRequest) {
        LessonResponseDTO createdLesson = lessonService.createLesson(lessonRequest);
        return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a lesson by id", description = "Updates an existing lesson identified by its id")
    @ApiResponse(responseCode = "200", description = "Lesson updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Lesson not found")
    @PutMapping("/{id}")
    public ResponseEntity<LessonResponseDTO> updateLesson(@PathVariable @Parameter(description = "Lesson id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody LessonRequestDTO lessonRequest) {
        return ResponseEntity.ok(lessonService.updateLesson(id, lessonRequest));
    }

    @Operation(summary = "Delete a lesson by id", description = "Deletes a lesson identified by its id")
    @ApiResponse(responseCode = "200", description = "Lesson deleted")
    @ApiResponse(responseCode = "404", description = "Lesson not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable @Parameter(description = "Lesson id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok("Lesson with id: " + id + " has been deleted successfully!");
    }
}
