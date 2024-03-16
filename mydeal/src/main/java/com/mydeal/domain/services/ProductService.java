package com.mydeal.domain.services;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.mapping.ProductMap;
import com.mydeal.domain.models.FilterModel;
import com.mydeal.domain.models.ProductDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public List<ProductDataModel> getAllProducts(FilterModel filter) {
        var em = JpaUtil.createEntityManager();
        ProductRepository pr = new ProductRepository();
        List<Product> productsEntities = pr.getAllProduct(em, filter);
        List<ProductDataModel> products = new ArrayList<>();
        ProductMap productMap = new ProductMap();
        for (Product p : productsEntities) {
            ProductDataModel productDataModel = productMap.convertEntityToModel(p);
            products.add(productDataModel);

        }
        em.close();
        return products;
    }

    public Integer getProductQuantity(int productId) {
        var em = JpaUtil.createEntityManager();
        ProductRepository pr = new ProductRepository();
        Product product = pr.read(em, productId);
        em.close();
        return product.getAvailableQuantity();
    }

}
