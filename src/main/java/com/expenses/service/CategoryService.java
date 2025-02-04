package com.expenses.service;

import com.expenses.entity.CategoryEntity;
import com.expenses.repository.CategoryRepository;
import com.expenses.exception.ApplicationException;
import com.expenses.constants.ErrorConstants;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.listAll();
    }

    @Transactional
    public void addCategory(String name) {
        CategoryEntity existingCategory = categoryRepository.findByName(name);
        if (existingCategory != null) {
            throw new ApplicationException(ErrorConstants.CATEGORY_ALREADY_EXISTS_CODE, ErrorConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
        }
    
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        categoryRepository.persist(category);
    }

    @Transactional
    public void deleteCategory(String name) {
        CategoryEntity existingCategory = categoryRepository.findByName(name);
        if (existingCategory == null) {
            throw new ApplicationException(ErrorConstants.CATEGORY_NOT_FOUND_CODE, ErrorConstants.CATEGORY_NOT_FOUND_MESSAGE);
        }
        categoryRepository.delete(existingCategory);
    }
}