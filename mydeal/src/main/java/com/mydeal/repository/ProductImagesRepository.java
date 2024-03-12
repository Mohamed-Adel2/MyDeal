package com.mydeal.repository;

import com.mydeal.domain.entities.ProductImages;

public class ProductImagesRepository extends CrudRepository<ProductImages>{
    public ProductImagesRepository() {
        setEntityClass(ProductImages.class);
    }
}
