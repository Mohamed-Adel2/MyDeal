package com.mydeal.domain.services;

import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CategoryRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryService {
    public List<String> getAllCategories() {
        EntityManager em = JpaUtil.createEntityManager();
        CategoryRepository categoryRepository = new CategoryRepository();
        List<String> categories = categoryRepository.getAllCategories(em);
        em.close();
        return categories;
    }

    public Integer getCategoryId(String category) {
        EntityManager em = JpaUtil.createEntityManager();
        CategoryRepository categoryRepository = new CategoryRepository();
        Integer categoryId = categoryRepository.getCategoryByName(em, category);
        em.close();
        return categoryId;
    }
}
