package com.expenses.repository;

import com.expenses.entity.CategoryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<CategoryEntity> {
    public CategoryEntity findByName(String name) {
        return find("name", name).firstResult();
    }
}