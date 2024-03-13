package com.mydeal.repository;

import com.mydeal.domain.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductRepository extends CrudRepository<Product> {
    public ProductRepository(EntityManager em)
    {
        super(em);
        setEntityClass(Product.class);
    }
    public List<Product> getAllProduct(){
        TypedQuery<Product> query = em.createQuery( "SELECT p FROM Product  p", Product.class );
        List<Product> data = query.getResultList();
        return data;
    }
}
