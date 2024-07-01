package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.CommentRequest;
import com.localWeb.localWeb.models.dto.response.CommentResponse;
import com.localWeb.localWeb.services.CommentService;
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
@RequestMapping("/api/v1/comments")
@Tag(name = "Comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Get all comments", description = "Retrieves a list of all comments")
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @Operation(summary = "Get comment by id", description = "Retrieves a comment by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)))
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable @Parameter(description = "Comment id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @Operation(summary = "Create a new comment", description = "Creates a new comment")
    @ApiResponse(responseCode = "201", description = "Comment created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)))
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse createdComment = commentService.createComment(commentRequest);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a comment by id", description = "Updates an existing comment identified by its id")
    @ApiResponse(responseCode = "200", description = "Comment updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)))
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable @Parameter(description = "Comment id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(id, commentRequest));
    }

    @Operation(summary = "Delete a comment by id", description = "Deletes a comment identified by its id")
    @ApiResponse(responseCode = "200", description = "Comment deleted")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable @Parameter(description = "Comment id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment with id: " + id + " has been deleted successfully!");
    }
}