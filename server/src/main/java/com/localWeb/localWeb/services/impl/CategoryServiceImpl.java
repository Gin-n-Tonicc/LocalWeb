package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.exceptions.category.CategoryCreateException;
import com.localWeb.localWeb.exceptions.category.CategoryNotFoundException;
import com.localWeb.localWeb.models.dto.common.CategoryDTO;
import com.localWeb.localWeb.models.entity.Category;
import com.localWeb.localWeb.repositories.CategoryRepository;
import com.localWeb.localWeb.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByDeletedAtIsNull();
        return categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    }

    @Override
    public CategoryDTO getCategoryById(UUID id) {
        Optional<Category> category = categoryRepository.findByIdAndDeletedAtIsNull(id);
        if (category.isPresent()) {
            return modelMapper.map(category.get(), CategoryDTO.class);
        }
        throw new CategoryNotFoundException(messageSource);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        try {
            Category categoryEntity = categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
            return modelMapper.map(categoryEntity, CategoryDTO.class);
        } catch (DataIntegrityViolationException exception) {
            // If a category with the same name already exists
            throw new CategoryCreateException(messageSource, true);
        }
    }

    @Override
    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategoryOptional = categoryRepository.findByIdAndDeletedAtIsNull(id);

        if (existingCategoryOptional.isEmpty()) {
            throw new CategoryNotFoundException(messageSource);
        }

        Category existingCategory = existingCategoryOptional.get();
        modelMapper.map(categoryDTO, existingCategory);

        existingCategory.setId(id);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findByIdAndDeletedAtIsNull(id);
        if (category.isPresent()) {
            // Soft delete
            category.get().setDeletedAt(LocalDateTime.now());
            categoryRepository.save(category.get());
        } else {
            throw new CategoryNotFoundException(messageSource);
        }
    }
}