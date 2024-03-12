package com.mydeal.repository;

import com.mydeal.domain.entities.Product;

public class ProductRepository extends CrudRepository<Product> {
    public ProductRepository() {
        setEntityClass(Product.class);
    }
}
