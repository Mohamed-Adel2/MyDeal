package com.mydeal.repository;

import com.mydeal.domain.entities.ProductImages;
import jakarta.persistence.EntityManager;

public class ProductImagesRepository extends CrudRepository<ProductImages>{
    public ProductImagesRepository()
    {
        setEntityClass(ProductImages.class);
    }
    @Override
    public void create(EntityManager em, ProductImages productImages){
        em.getTransaction().begin();
        em.persist(productImages);
        em.getTransaction().commit();
    }
}
