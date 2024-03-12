package com.mydeal.repository;

import com.mydeal.domain.entities.Category;

public class CategoryRepository extends CrudRepository<Category> {
    public CategoryRepository() {
        setEntityClass(Category.class);
    }
}
