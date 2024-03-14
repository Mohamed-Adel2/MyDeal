package com.mydeal.repository;

import com.mydeal.domain.entities.ProductImages;
import jakarta.persistence.EntityManager;

public class ProductImagesRepository extends CrudRepository<ProductImages>{
    public ProductImagesRepository(EntityManager em)
    {
        setEntityClass(ProductImages.class);
    }
}
