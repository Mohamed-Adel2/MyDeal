package com.mydeal.repository;

import com.mydeal.domain.entities.Category;
import com.mydeal.domain.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;

public class CategoryRepository extends CrudRepository<Category> {
    public CategoryRepository() {
        setEntityClass(Category.class);
    }

    public List<String> getAllCategories(EntityManager em) {
        TypedQuery<String> query = em.createQuery("SELECT c.categoryName FROM Category c where c.isRemoved = 0", String.class);
        return query.getResultList();
    }

    public Integer getCategoryByName(EntityManager em, String categoryName) {
        System.out.println(categoryName);
        TypedQuery<Integer> query = em.createQuery("SELECT c.id FROM Category c " +
                (!categoryName.equals("All") ? " WHERE c.categoryName = :categoryName" : "") + " and c.isRemoved = 0", Integer.class);
        if (!categoryName.equals("All"))
            query.setParameter("categoryName", categoryName);
        if(query.getResultList().isEmpty())
            return -1;
        return query.getSingleResult();
    }

    public Category getCategory(EntityManager em, String categoryName) {
        System.out.println(categoryName);
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c " +
                (!categoryName.equals("All") ? " WHERE c.categoryName = :categoryName" : "") + " and c.isRemoved = 0", Category.class);
        if (!categoryName.equals("All"))
            query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }

    public Integer createNewCategory(EntityManager em, String categoryName) {
        TypedQuery<Integer> query = em.createQuery("SELECT c.id FROM Category c WHERE c.categoryName = :categoryName and c.isRemoved = 0", Integer.class);
        query.setParameter("categoryName", categoryName);
        List<Integer> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
            return category.getId();
        } else {
            return -1;
        }
    }

    public boolean deleteCategory(EntityManager em, String categoryName) {
        TypedQuery<Integer> query = em.createQuery("SELECT c.id FROM Category c WHERE c.categoryName = :categoryName and c.isRemoved = 0", Integer.class);
        query.setParameter("categoryName", categoryName);
        List<Integer> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            System.out.println("Category Name " + categoryName);
            Integer categoryId = resultList.getFirst(); // Assuming there's only one category with the same name
            Category category = em.find(Category.class, categoryId);
            category.setIsRemoved(1);
            ProductRepository productRepository = new ProductRepository();
            CustomerCartRepository customerCartRepository = new CustomerCartRepository();
            em.getTransaction().begin();
            update(em, category);
            Set<Product> products = category.getProducts();
            for (Product p : products) {
                p.setIsDeleted(1);
                customerCartRepository.deleteProductFromCustomerCart(em, p.getId());
                productRepository.update(em, p);
            }
            em.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }
}
