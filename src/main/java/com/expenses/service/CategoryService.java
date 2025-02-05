package com.expenses.service;

import com.expenses.entity.CategoryEntity;
import com.expenses.repository.CategoryRepository;
import com.expenses.exception.ApplicationException;
import com.expenses.constants.ErrorConstants;
import com.expenses.dto.SubCategoryDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        try {
            return categoryRepository.listAll();
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    @Transactional
    public void createCategory(String name) {
        try {
            CategoryEntity existingCategory = categoryRepository.findByName(name);
            if (existingCategory != null) {
                throw new ApplicationException(ErrorConstants.CATEGORY_ALREADY_EXISTS_CODE, ErrorConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
            }
        
            CategoryEntity category = new CategoryEntity();
            category.setName(name);
            categoryRepository.persist(category);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    @Transactional
    public void deleteCategory(String name) {
        try {
            CategoryEntity existingCategory = categoryRepository.findByName(name);
            if (existingCategory == null) {
                throw new ApplicationException(ErrorConstants.CATEGORY_NOT_FOUND_CODE, ErrorConstants.CATEGORY_NOT_FOUND_MESSAGE);
            }
            categoryRepository.delete(existingCategory);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    @Transactional
    public void createSubCategory(SubCategoryDTO subCategoryDTO) {
        try {
            CategoryEntity category = categoryRepository.findByName(subCategoryDTO.getCategoryName());
            if (category == null) {
                throw new ApplicationException(ErrorConstants.CATEGORY_NOT_FOUND_CODE, ErrorConstants.CATEGORY_NOT_FOUND_MESSAGE);
            }

            List<String> subCategories = category.getSubCategories();
            if (subCategories.contains(subCategoryDTO.getName())) {
                throw new ApplicationException(ErrorConstants.CATEGORY_ALREADY_EXISTS_CODE, ErrorConstants.SUB_CATEGORY_ALREADY_EXISTS_MESSAGE);
            }
            subCategories.add(subCategoryDTO.getName());
            category.setSubCategories(subCategories);
            categoryRepository.persist(category);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}