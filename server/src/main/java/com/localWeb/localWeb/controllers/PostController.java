package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.request.PostRequest;
import com.localWeb.localWeb.models.dto.response.PostResponse;
import com.localWeb.localWeb.services.PostService;
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
@RequestMapping("/api/v1/posts")
@Tag(name = "Posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Get all posts", description = "Retrieves a list of all posts")
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Operation(summary = "Get post by id", description = "Retrieves a post by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)))
    @ApiResponse(responseCode = "404", description = "Post not found")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable @Parameter(description = "Post id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "Create a new post", description = "Creates a new post")
    @ApiResponse(responseCode = "201", description = "Post created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)))
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        PostResponse createdPost = postService.createPost(postRequest);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a post by id", description = "Updates an existing post identified by its id")
    @ApiResponse(responseCode = "200", description = "Post updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)))
    @ApiResponse(responseCode = "404", description = "Post not found")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable @Parameter(description = "Post id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.updatePost(id, postRequest));
    }

    @Operation(summary = "Delete a post by id", description = "Deletes a post identified by its id")
    @ApiResponse(responseCode = "200", description = "Post deleted")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable @Parameter(description = "Post id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post with id: " + id + " has been deleted successfully!");
    }
}