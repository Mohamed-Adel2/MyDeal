package com.mydeal.repository;

import com.mydeal.domain.entities.Category;
import jakarta.persistence.EntityManager;

public class CategoryRepository extends CrudRepository<Category> {
    public CategoryRepository(EntityManager em) {
        setEntityClass(Category.class);
    }
}
