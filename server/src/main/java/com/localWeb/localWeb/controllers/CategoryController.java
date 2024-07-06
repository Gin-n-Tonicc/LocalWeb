package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.dto.common.CategoryDTO;
import com.localWeb.localWeb.services.CategoryService;
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
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories")
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Get category by id", description = "Retrieves a category by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Category not found")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") @Parameter(description = "Category id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Create a new category", description = "Creates a new category")
    @ApiResponse(responseCode = "201", description = "Category created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)))
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a category by id", description = "Updates an existing category identified by its id")
    @ApiResponse(responseCode = "200", description = "Category updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Category not found")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") @Parameter(description = "Category id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @Operation(summary = "Delete a category by id", description = "Deletes a category identified by its id")
    @ApiResponse(responseCode = "200", description = "Category deleted")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable("id") @Parameter(description = "Category id", example = "123e4567-e89b-12d3-a456-426614174000") UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category with id: " + id + " has been deleted successfully!");
    }
}