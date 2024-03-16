package com.mydeal.repository;

import com.mydeal.domain.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CategoryRepository extends CrudRepository<Category> {
    public CategoryRepository() {
        setEntityClass(Category.class);
    }

    public List<String> getAllCategories(EntityManager em) {
        TypedQuery<String> query = em.createQuery("SELECT c.categoryName FROM Category c", String.class);
        return query.getResultList();
    }
}
