package com.mydeal.repository;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.models.FilterModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductRepository extends CrudRepository<Product> {
    public ProductRepository() {
        setEntityClass(Product.class);
    }

    public List<Product> getAllProduct(EntityManager em, FilterModel filter) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.price >= :minPrice"
                + (filter.getCategoryId() == 0 ? "" : " AND p.category.id = :category ")
                + " AND p.price <= :maxPrice AND p.productName LIKE :searchKey", Product.class);
        query.setParameter("minPrice", filter.getMinPrice());
        query.setParameter("maxPrice", filter.getMaxPrice());
        query.setParameter("searchKey", "%" + filter.getSearchKey() + "%");
        if (filter.getCategoryId() != 0) {
            query.setParameter("category", filter.getCategoryId());
        }
        query.setFirstResult(filter.getStartIdx());
        query.setMaxResults(filter.getLimit());
        return query.getResultList();
    }

    public Product getProduct(EntityManager em , int id){
        Product product = em.find(Product.class, id);
        return product;
    }
    public int addProduct(EntityManager em , Product product){
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
        return product.getId();
    }
    public boolean deleteProduct(EntityManager em , Product product){
        try {
            System.out.println("We are here");
            em.getTransaction().begin();
            em.remove(product);
            em.getTransaction().commit();
            // Operation succeeded
            System.out.println("Product removed successfully");
            return true;
        } catch (Exception e) {
            // Operation failed
            System.err.println("Failed to remove product: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback the transaction if it's still active
            }
            return false;
        }

    }
    public boolean updateProduct(EntityManager em , Product product){
        try {
            em.getTransaction().begin();
            Product updatedProduct = em.merge(product); // Perform the merge operation
            em.getTransaction().commit();

            if (updatedProduct  != null) {
                // Merge operation was successful
                System.out.println("Merge operation successful");
                return true;
            } else {
                // Merge operation failed or did not result in a managed entity
                System.out.println("Merge operation failed or no changes were made");
                return false;
            }
        } catch (Exception e) {
            // Handle exceptions
            System.err.println("Failed to merge entity: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback the transaction if it's still active
            }
            return false;
        }
    }

}
