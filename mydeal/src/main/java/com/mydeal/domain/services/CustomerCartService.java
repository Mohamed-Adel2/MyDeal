package com.mydeal.domain.services;

import com.mydeal.domain.entities.CustomerCart;
import com.mydeal.domain.entities.CustomerCartId;
import com.mydeal.domain.mapping.CartMapping;
import com.mydeal.domain.models.CartModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerCartRepository;
import jakarta.persistence.EntityManager;


public class CustomerCartService {
    public void addProductToCart(CartModel cartModel) {
        CustomerCartId customerCartId = new CustomerCartId(cartModel.getCustomerId(), cartModel.getProductId());
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        EntityManager em = JpaUtil.createEntityManager();
        CustomerCart customerCart = customerCartRepository.read(em, customerCartId);
        em.getTransaction().begin();
        if (customerCart == null) {
            customerCart = CartMapping.mapToCustomerCart(cartModel);
            customerCartRepository.create(em, customerCart);
        } else {
            customerCart.setQuantity(customerCart.getQuantity() + cartModel.getQuantity());
            customerCartRepository.update(em, customerCart);
        }
        em.getTransaction().commit();
        em.close();
    }
}
