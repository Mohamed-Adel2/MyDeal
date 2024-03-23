package com.mydeal.repository;

import com.mydeal.domain.entities.ProductImages;
import jakarta.persistence.EntityManager;

import java.util.List;

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
    public int add(EntityManager em , ProductImages productImages){
        System.out.println("From repo "+ productImages.getId());
        em.getTransaction().begin();
        em.persist(productImages);
        em.getTransaction().commit();
        return productImages.getId();
    }
    public boolean deleteImage(EntityManager em , ProductImages productImages){
        try {
            em.getTransaction().begin();
            if (!em.contains(productImages)) {
                productImages = em.merge(productImages);
            }
            em.remove(productImages);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        }
    }
}
