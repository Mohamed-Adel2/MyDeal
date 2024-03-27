package com.mydeal.domain.mapping.admin;

import com.mydeal.domain.entities.Category;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.models.admin.UpdateProductModel;
import com.mydeal.domain.services.CategoryService;
import com.mydeal.domain.services.ProductService;
import com.mydeal.repository.ProductRepository;

public class UpdateProductModelToProductMap {

    private Product product;
    private UpdateProductModel updateProductModel;

    public Product convertModelToEntity(UpdateProductModel updateProductModel) {
        this.updateProductModel = updateProductModel;
        product = new Product();
        product.setId(updateProductModel.getProductId());
        product.setProductName(updateProductModel.getProductName());
        CategoryService categoryService = new CategoryService();
        Category category = categoryService.getCategoryByName(updateProductModel.getCategory());
        product.setCategory(category);
        product.setDescription(updateProductModel.getDescription());
        product.setAvailableQuantity(updateProductModel.getQuantity());
        product.setPrice(updateProductModel.getPrice());
        return product;
    }

}
