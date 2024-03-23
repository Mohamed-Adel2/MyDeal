package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.models.ProductDetailDataModel;

import java.util.*;

public class ProductDetailMap {
    private Product product;

    private ProductDetailDataModel productDetailDataModel;

    public ProductDetailDataModel convertEntityToModel(Product product) {
        ProductImageMap productImageMap = new ProductImageMap();
        this.productDetailDataModel = new ProductDetailDataModel();
        productDetailDataModel.setId(product.getId());
        productDetailDataModel.setProductName(product.getProductName());
        Set<ProductImages> productImageEntity = product.getProductimages();
        byte arr[][] = new byte[productImageEntity.size()+1][];
        int [] idsOfImages = new int[productImageEntity.size()+1];
        int i =0;
        for (ProductImages entity : productImageEntity) {
                arr[i] = entity.getImage();
                idsOfImages[i] = entity.getId();
                i++;
        }
        productDetailDataModel.setImages(arr);
        productDetailDataModel.setIdsOfImages(idsOfImages);
        productDetailDataModel.setDescription(product.getDescription());
        productDetailDataModel.setPrice(Math.round(product.getPrice() * 100.0) / 100.0);
        productDetailDataModel.setAvailableQuantity(product.getAvailableQuantity());
        productDetailDataModel.setCategory(product.getCategory().getCategoryName());
        productDetailDataModel.setIsRemoved(product.getIsDeleted());
        productDetailDataModel.setAverageRating(Math.round(product.getAverageRating() * 100.0) / 100.0);
        return productDetailDataModel;
    }

    public Product convertModelToEntity(ProductDetailDataModel productDetailDataModel) {
        ProductImageMap productImageMap = new ProductImageMap();
        this.product = new Product();
        product.setId(productDetailDataModel.getId());
        product.setProductName(productDetailDataModel.getProductName());
        product.setDescription(productDetailDataModel.getDescription());
        product.setPrice(Math.round(productDetailDataModel.getPrice() * 100.0) / 100.0);
        product.setAverageRating(Math.round(productDetailDataModel.getAverageRating() * 100.0) / 100.0);
        product.setAvailableQuantity(productDetailDataModel.getAvailableQuantity());
        product.setIsDeleted(productDetailDataModel.getIsRemoved());
        return product;
    }
}
