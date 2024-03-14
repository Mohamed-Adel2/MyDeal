package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.models.ProductDataModel;
import lombok.Data;

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
        productDataModel.setCategory(product.getCategory());
        productDataModel.setAvailableQuantity(product.getAvailableQuantity());
        return productDataModel;
    }
}
