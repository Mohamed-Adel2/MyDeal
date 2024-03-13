package com.mydeal.domain.services;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.mapping.ProductMap;
import com.mydeal.domain.models.ProductDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public static List<ProductDataModel> getAllProducts(){
        var em= JpaUtil.createEntityManager();
        ProductRepository pr = new ProductRepository(em);
        List<Product> productsEntities = pr.getAllProduct();
        List<ProductDataModel> products = new ArrayList<>();
        ProductMap productMap = new ProductMap();
        for(Product p:productsEntities){
            products.add(productMap.convertEntityToModel(p));
        }
        em.close();
        return products;
    }
}