package com.mydeal.domain.services;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.entities.CustomerCart;
import com.mydeal.domain.entities.CustomerCartId;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.mapping.CartMapping;
import com.mydeal.domain.models.CartModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerCartRepository;
import com.mydeal.repository.CustomerRepository;
import com.mydeal.repository.ProductRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;


public class CustomerCartService {
    public void addProductToCart(CartModel cartModel) {
        CustomerCartId customerCartId = new CustomerCartId(cartModel.getCustomerId(), cartModel.getProductId());
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        EntityManager em = JpaUtil.createEntityManager();
        CustomerCart customerCart = customerCartRepository.read(em, customerCartId);
        Customer customer = customerRepository.read(em, cartModel.getCustomerId());
        Product product = productRepository.read(em, cartModel.getProductId());
        em.getTransaction().begin();
        if (customerCart == null) {
            customerCart = CartMapping.mapToCustomerCart(cartModel);
            customerCart.setCustomer(customer);
            customerCart.setProduct(product);
            customerCartRepository.create(em, customerCart);
        } else {
            customerCart.setQuantity(customerCart.getQuantity() + cartModel.getQuantity());
            customerCartRepository.update(em, customerCart);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void removeProductFromCart(CartModel cartModel) {
        CustomerCartId customerCartId = new CustomerCartId(cartModel.getCustomerId(), cartModel.getProductId());
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        EntityManager em = JpaUtil.createEntityManager();
        CustomerCart customerCart = customerCartRepository.read(em, customerCartId);
        if (customerCart != null) {
            em.getTransaction().begin();
            customerCartRepository.delete(em, customerCart);
            em.getTransaction().commit();
        }
        em.close();
    }

    public void updateProductQuantity(CartModel cartModel) {
        CustomerCartId customerCartId = new CustomerCartId(cartModel.getCustomerId(), cartModel.getProductId());
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        EntityManager em = JpaUtil.createEntityManager();
        CustomerCart customerCart = customerCartRepository.read(em, customerCartId);
        if (customerCart == null) {
            return;
        }
        em.getTransaction().begin();
        customerCart.setQuantity(Math.max(0, cartModel.getQuantity()));
        customerCartRepository.update(em, customerCart);
        em.getTransaction().commit();
        em.close();

    }

    public ArrayList<CartModel> getCustomerCartItems(int customerId) {
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        EntityManager em = JpaUtil.createEntityManager();
        ArrayList<CustomerCart> cart = customerCartRepository.getCustomerCart(em, customerId);
        em.close();
        ArrayList<CartModel> cartModels = new ArrayList<>();
        for (CustomerCart c : cart) {
            cartModels.add(CartMapping.mapToCartModel(c));
        }
        return cartModels;
    }

    public int getCartItemsPrice(int customerId) {
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        EntityManager em = JpaUtil.createEntityManager();
        int ret = customerCartRepository.getCustomerCartPrice(em, customerId);
        em.close();
        return ret;
    }

    public void addCartItems(ArrayList<CartModel> cartItems, int customerId) {
        for (CartModel cartModel : cartItems) {
            cartModel.setCustomerId(customerId);
            addProductToCart(cartModel);
        }
    }
}
