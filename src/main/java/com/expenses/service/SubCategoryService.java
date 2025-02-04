package com.expenses.service;

import com.expenses.entity.SubCategoryEntity;
import com.expenses.repository.CategoryRepository;
import com.expenses.repository.SubCategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SubCategoryService {

    @Inject
    SubCategoryRepository subCategoryRepository;

    @Inject
    CategoryRepository categoryRepository;

    public List<SubCategoryEntity> getSubCategoriesByCategory(Long categoryId) {
        return subCategoryRepository.find("category.id", categoryId).list();
    }

    @Transactional
    public SubCategoryEntity addSubCategory(Long categoryId, String name) {
        SubCategoryEntity subCategory = new SubCategoryEntity();
        subCategory.setName(name);
        subCategory.setCategory(categoryRepository.findById(categoryId));
        subCategoryRepository.persist(subCategory);
        return subCategory;
    }
}