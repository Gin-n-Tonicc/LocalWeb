package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.models.dto.common.CategoryDTO;
import com.localWeb.localWeb.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<CategoryDTO> getAllCategories() {
        return List.of();
    }

    @Override
    public CategoryDTO getCategoryById(UUID id) {
        return null;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {

    }
}
