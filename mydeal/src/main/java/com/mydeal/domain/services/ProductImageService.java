package com.mydeal.domain.services;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.mapping.ProductImageMap;
import com.mydeal.domain.mapping.admin.UpdateProductModelToImageMap;
import com.mydeal.domain.models.ProductImageModel;
import com.mydeal.domain.models.admin.AddImageModel;
import com.mydeal.domain.models.admin.UpdateProductModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.ProductImagesRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

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
    public int addNewImagesToProduct(UpdateProductModel updateProductModel){
        EntityManager em = JpaUtil.createEntityManager();
        int ImagesAdded = 0;
        ProductImagesRepository productImagesRepository = new ProductImagesRepository();
        UpdateProductModelToImageMap updateProductModelToImageMap = new UpdateProductModelToImageMap();
        List<ProductImages> productImages = updateProductModelToImageMap.getAddedImages(updateProductModel);
        for(int i=0;i<productImages.size();i++){
            int Id = productImagesRepository.add(em , productImages.get(i));
            if(Id>0){
                ImagesAdded++;
            }
        }
        em.close();
        return ImagesAdded;
    }
    public int deletedImagesToProduct(UpdateProductModel updateProductModel){
        EntityManager em = JpaUtil.createEntityManager();
        int ImagesDeleted = 0;
        ProductImagesRepository productImagesRepository = new ProductImagesRepository();
        UpdateProductModelToImageMap updateProductModelToImageMap = new UpdateProductModelToImageMap();
        List<ProductImages> DeletedProductImages = updateProductModelToImageMap.getDeletedImages(updateProductModel);
        for(ProductImages productImages: DeletedProductImages){
            boolean deleted = productImagesRepository.deleteImage(em ,productImages);
            if(deleted){
                ImagesDeleted++;
            }
        }
        em.close();
        return ImagesDeleted;
    }

}
