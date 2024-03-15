package com.mydeal.repository;

import com.mydeal.domain.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductRepository extends CrudRepository<Product> {
    public ProductRepository(EntityManager em) {
        setEntityClass(Product.class);
    }
   public List<Product> getAllProduct(EntityManager em){
        TypedQuery<Product> query = em.createQuery( "SELECT p FROM Product  p", Product.class );
       query.setMaxResults(7);
        List<Product> data = query.getResultList();
        System.out.println(data.size());
        return data;
    }
}
