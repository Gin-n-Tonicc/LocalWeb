package com.localWeb.localWeb.service;

import com.localWeb.localWeb.models.dto.common.CategoryDTO;
import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(UUID id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO);

    void deleteCategory(UUID id);
}
