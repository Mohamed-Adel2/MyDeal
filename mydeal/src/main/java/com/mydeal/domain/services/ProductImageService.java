package com.mydeal.domain.services;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.mapping.ProductImageMap;
import com.mydeal.domain.models.ProductImageModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.ProductImagesRepository;
import jakarta.persistence.EntityManager;

public class ProductImageService {

    public void setImage(ProductImageModel productImageModel){
        ProductImageMap productImageMap = new ProductImageMap();
        ProductImages product =   productImageMap.convertModelToEntity(productImageModel);
        EntityManager em = JpaUtil.createEntityManager();
        ProductImagesRepository productImagesRepository = new ProductImagesRepository();
        productImagesRepository.create(em,product);
        em.close();
    }
}
