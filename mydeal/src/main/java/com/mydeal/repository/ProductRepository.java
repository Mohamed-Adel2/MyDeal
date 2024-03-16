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
}
