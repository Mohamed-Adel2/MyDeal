package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.models.ProductDataModel;
import lombok.Data;

import java.util.Set;

@Data
public class ProductMap {
    Product product;

    ProductDataModel productDataModel;

    public ProductMap(){

    }
   public ProductMap(Product product){
        this.product= product;
    }
    public ProductMap(ProductDataModel productDataModel){
        this.productDataModel = productDataModel;
    }
   public ProductDataModel convertEntityToModel(Product product){
        this.product = product;
        productDataModel = new ProductDataModel();
        productDataModel.setId(product.getId());
        productDataModel.setProductName(product.getProductName());
        productDataModel.setDescription(product.getDescription());
        productDataModel.setPrice(product.getPrice());
        Set<ProductImages> arrImages = product.getProductimages();
        byte[] arr;
        for(ProductImages productImages:arrImages){
            arr = productImages.getImage();
            productDataModel.setImage(arr);
            break;
        }
        productDataModel.setAvailableQuantity(product.getAvailableQuantity());
        return productDataModel;
    }
}
