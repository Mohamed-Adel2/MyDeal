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

    public Integer getCategoryByName(EntityManager em, String categoryName) {
        System.out.println(categoryName);
        TypedQuery<Integer> query = em.createQuery("SELECT c.id FROM Category c " +
                (!categoryName.equals("All") ? " WHERE c.categoryName = :categoryName" : ""), Integer.class);
        if (!categoryName.equals("All"))
            query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }
    public Integer createNewCategory(EntityManager em , String categoryName){
        TypedQuery<Integer> query = em.createQuery("SELECT c.id FROM Category c WHERE c.categoryName = :categoryName",Integer.class);
            query.setParameter("categoryName", categoryName);
        List<Integer> resultList = query.getResultList();
        if(resultList.isEmpty()){
            Category category = new Category();
            category.setCategoryName(categoryName);
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
            return category.getId();
        }else{
            return -1;
        }
    }
    public boolean deleteCategory(EntityManager em , String categoryName){
        TypedQuery<Integer> query = em.createQuery("SELECT c.id FROM Category c WHERE c.categoryName = :categoryName",Integer.class);
        query.setParameter("categoryName", categoryName);
        List<Integer> resultList = query.getResultList();
        if(!resultList.isEmpty()){
            System.out.println("Category Name "+categoryName);
            Integer categoryId = resultList.getFirst(); // Assuming there's only one category with the same name
            Category category = em.find(Category.class,categoryId);
            em.getTransaction().begin();
            em.remove(category);
            em.getTransaction().commit();
            return true;
        }else{
                return false;
        }
    }
}
