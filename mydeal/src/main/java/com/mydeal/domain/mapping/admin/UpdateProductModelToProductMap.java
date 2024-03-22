package com.mydeal.domain.mapping.admin;

import com.mydeal.domain.entities.Category;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.models.admin.UpdateProductModel;

public class UpdateProductModelToProductMap {

    private Product product;
    private UpdateProductModel updateProductModel;

    public Product convertModelToEntity(UpdateProductModel updateProductModel){
        this.updateProductModel = updateProductModel;
        product = new Product();
        product.setId(updateProductModel.getProductId());
        product.setProductName(updateProductModel.getProductName());
        Category category = new Category();
        category.setId(updateProductModel.getCategory());
        product.setCategory(category);
        product.setDescription(updateProductModel.getDescription());
        product.setAvailableQuantity(updateProductModel.getQuantity());
        product.setPrice(updateProductModel.getPrice());
        return product;
    }

}
