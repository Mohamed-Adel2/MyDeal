package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Category;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.entities.ProductImages;
import com.mydeal.domain.models.ProductDataModel;
import lombok.Data;

import java.util.Set;

@Data
public class ProductMap {
    Product product;

    ProductDataModel productDataModel;

    public ProductMap() {

    }

    public ProductMap(Product product) {
        this.product = product;
    }

    public ProductMap(ProductDataModel productDataModel) {
        this.productDataModel = productDataModel;
    }

    public ProductDataModel convertEntityToModel(Product product) {
        this.product = product;
        productDataModel = new ProductDataModel();
        productDataModel.setId(product.getId());
        productDataModel.setProductName(product.getProductName());
        productDataModel.setDescription(product.getDescription());
        productDataModel.setPrice(Math.round(product.getPrice() * 100.0) / 100.0);
        productDataModel.setCategory(product.getCategory().getId());
        productDataModel.setIsRemoved(product.getIsDeleted());
        Set<ProductImages> arrImages = product.getProductimages();
        byte[] arr;
        for (ProductImages productImages : arrImages) {
            arr = productImages.getImage();
            productDataModel.setImage(arr);
            break;
        }
        productDataModel.setAvailableQuantity(product.getAvailableQuantity());
        return productDataModel;
    }
    public Product convertModeToEntity(ProductDataModel productDataModel){
        Category category = new Category();
        category.setId(productDataModel.getCategory());
        this.productDataModel = new ProductDataModel();
       product = new Product();
       product.setId(productDataModel.getId());
       product.setCategory(category);
       product.setProductName(productDataModel.getProductName());
       product.setPrice(productDataModel.getPrice());
       product.setDescription(productDataModel.getDescription());
       product.setAvailableQuantity(product.getAvailableQuantity());
       return product;
    }
}
