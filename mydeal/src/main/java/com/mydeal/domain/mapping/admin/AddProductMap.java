package com.mydeal.domain.mapping.admin;

import com.mydeal.domain.entities.Category;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.models.admin.AddProductModel;

public class AddProductMap {
    Product product;

    AddProductModel addProductModel;

    public Product convertModelToEntity(AddProductModel addProductModel){
        product = new Product();
        product.setProductName(addProductModel.getProductName());
        product.setAvailableQuantity(addProductModel.getAvailableQuantity());
        product.setPrice(addProductModel.getPrice());
        product.setDescription(addProductModel.getDescription());
        product.setAverageRating(addProductModel.getRating());
        Category category = new Category();
        category.setId(addProductModel.getCategory());
        product.setCategory(category);
        return product;
    }
}
