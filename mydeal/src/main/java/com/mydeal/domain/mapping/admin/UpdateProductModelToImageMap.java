package com.mydeal.domain.mapping.admin;

import com.mydeal.domain.entities.Category;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.models.admin.UpdateProductModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductModelToImageMap {



    public List<ProductImages> getDeletedImages(UpdateProductModel updateProductModel){


       List<ProductImages> deletedImages = new ArrayList<>();
               for(int i: updateProductModel.getDeleted()){
                   ProductImages productImage = new ProductImages();
                   productImage.setId(i);
                   deletedImages.add(productImage);
               }
        return deletedImages;
    }
    public List<ProductImages> getAddedImages(UpdateProductModel updateProductModel){
        List<ProductImages> addedImages = new ArrayList<>();
        for(byte[] image: updateProductModel.getAdded()){
            ProductImages productImages = new ProductImages();
            Product product = new Product();
            product.setId(updateProductModel.getProductId());
            productImages.setProduct(product);
            productImages.setImage(image);
            addedImages.add(productImages);
        }
        return addedImages;
    }

}
