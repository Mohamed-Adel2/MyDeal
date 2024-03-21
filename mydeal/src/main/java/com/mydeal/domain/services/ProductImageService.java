package com.mydeal.domain.services;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.mapping.ProductImageMap;
import com.mydeal.domain.models.ProductImageModel;
import com.mydeal.domain.models.admin.AddImageModel;
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
    public int setImages(ProductImageModel[] images){
        EntityManager em = JpaUtil.createEntityManager();
        int ImagesAdded=0;
        ProductImagesRepository productImagesRepository = new ProductImagesRepository();
        ProductImageMap productImageMap = new ProductImageMap();
        for(int i=0;i<images.length-1;i++){
            ProductImages productImages = productImageMap.convertModelToEntity(images[i]);
           int Id=productImagesRepository.add(em , productImages);
           if(Id>0){
               ImagesAdded++;
           }
        }
        em.close();
        return ImagesAdded;
    }
}
