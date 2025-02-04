package com.expenses.repository;

import com.expenses.entity.SubCategoryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubCategoryRepository implements PanacheRepository<SubCategoryEntity> {
}