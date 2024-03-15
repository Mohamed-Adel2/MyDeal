package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.models.ProductImageModel;

public class ProductImageMap {

    ProductImageModel productImageModel;

    ProductImages productImages;

    public ProductImageMap(ProductImageModel productImageModel) {
        this.productImageModel = productImageModel;
    }

    public ProductImageMap(ProductImages productImages) {
        this.productImages = productImages;
    }
    public ProductImageMap(){

    }
    public ProductImageModel convertEntityToModel(ProductImages productImages){
        this.productImages = productImages;
        productImageModel = new ProductImageModel();
        productImageModel.setProductId(productImageModel.getProductId());
        productImageModel.setImage(productImageModel.getImage());
        return productImageModel;
    }
    public ProductImages convertModelToEntity(ProductImageModel productImageModel){
        this.productImageModel = productImageModel;
        productImages = new ProductImages();
        Product product = new Product();
        product.setId(productImageModel.getProductId());
        productImages.setProduct(product);
        productImages.setImage(productImageModel.getImage());
        return productImages;
    }
}
