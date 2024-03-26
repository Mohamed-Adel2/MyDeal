package com.mydeal.domain.services;

import com.mydeal.domain.entities.Product;
import com.mydeal.domain.mapping.ProductDetailMap;
import com.mydeal.domain.mapping.ProductMap;
import com.mydeal.domain.mapping.admin.AddProductMap;
import com.mydeal.domain.mapping.admin.UpdateProductModelToProductMap;
import com.mydeal.domain.models.FilterModel;
import com.mydeal.domain.models.ProductDataModel;
import com.mydeal.domain.models.ProductDetailDataModel;
import com.mydeal.domain.models.admin.AddProductModel;
import com.mydeal.domain.models.admin.UpdateProductModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerCartRepository;
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

    public ProductDetailDataModel getProduct(int id) {
        var em = JpaUtil.createEntityManager();
        ProductRepository pr = new ProductRepository();
        Product product = pr.getProduct(em, id);
        if (product == null) {
            em.close();
            return null;
        }
        ProductDetailMap productMap = new ProductDetailMap();
        ProductDetailDataModel productDataModel = productMap.convertEntityToModel(product);
        em.close();
        return productDataModel;
    }


    public Integer getProductQuantity(int productId) {
        var em = JpaUtil.createEntityManager();
        ProductRepository pr = new ProductRepository();
        Product product = pr.read(em, productId);
        em.close();
        return product.getAvailableQuantity();
    }

    public int addProduct(AddProductModel addProductModel) {
        var em = JpaUtil.createEntityManager();
        AddProductMap addProductMap = new AddProductMap();
        Product product = addProductMap.convertModelToEntity(addProductModel);
        if (product.getCategory().getId() == -1) {
            em.close();
            return -1;
        }
        ProductRepository pr = new ProductRepository();
        int id = pr.addProduct(em, product);
        em.close();
        return id;
    }

    public boolean deleteProduct(int id) {
        var em = JpaUtil.createEntityManager();
        Product product = em.find(Product.class, id);
        product.setIsDeleted(1);

        //make quantity equal 0 to prevent  making order
        product.setAvailableQuantity(0);

        ProductRepository pr = new ProductRepository();
        em.getTransaction().begin();
        product = pr.update(em, product);
        em.getTransaction().commit();
        em.close();
        return product.getIsDeleted() == 1;
    }

    public boolean updateProduct(UpdateProductModel updateProductModel) {
        var em = JpaUtil.createEntityManager();
        // ProductMap productMap = new ProductMap();
        UpdateProductModelToProductMap updateProductModelToProductMap = new UpdateProductModelToProductMap();
        Product product = updateProductModelToProductMap.convertModelToEntity(updateProductModel);
        Product originalProduct = em.find(Product.class, product.getId());
        if (originalProduct.getIsDeleted() == 1) {
            em.close();
            return false;
        }
        ProductRepository pr = new ProductRepository();
        em.getTransaction().begin();
        boolean update = pr.update(em, product) != null;
        em.getTransaction().commit();
        em.close();
        return update;
    }
}
