package com.mydeal.domain.services;

import com.mydeal.domain.entities.Category;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CategoryRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryService {
    public List<String> getAllCategories() {
        EntityManager em = JpaUtil.createEntityManager();
        CategoryRepository categoryRepository = new CategoryRepository();
        List<String> categories = categoryRepository.getAllCategories(em);
        for(String cat:categories){
            System.out.println(cat);
        }
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

    public Category getCategoryByName(String category) {
        EntityManager em = JpaUtil.createEntityManager();
        CategoryRepository categoryRepository = new CategoryRepository();
        Category category1 = categoryRepository.getCategory(em, category);
        em.close();
        return category1;
    }

    public int AddCategory(String categoryName){
        EntityManager em = JpaUtil.createEntityManager();
        CategoryRepository categoryRepository = new CategoryRepository();
        Integer categoryId = categoryRepository.createNewCategory(em,categoryName);
        em.close();
        return  categoryId;
    }
    public boolean deleteCategory(String categoryName){
        EntityManager em = JpaUtil.createEntityManager();
        CategoryRepository categoryRepository = new CategoryRepository();
        boolean deleteCategory = categoryRepository.deleteCategory(em ,categoryName);
        em.close();
        return deleteCategory;
    }

}
