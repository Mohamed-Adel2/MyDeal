package com.mydeal.domain.services;

import com.mydeal.domain.entities.*;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.*;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;

public class CheckOutService {
    public boolean makeOrder(int customerId, double orderPrice) {
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        OrderDetailsRepository orderDetailsRepository = new OrderDetailsRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        OrderRepository orderRepository = new OrderRepository();
        ProductRepository productRepository = new ProductRepository();
        EntityManager em = JpaUtil.createEntityManager();
        ArrayList<CustomerCart> cart = getCustomerCart(em, customerId);
        Customer customer = getCustomer(em, customerId);
        customer.setCreditLimit(customer.getCreditLimit() - orderPrice);
        Order order = createOrder(customer);
        em.getTransaction().begin();
        orderRepository.create(em, order);
        for (CustomerCart customerCart : cart) {
            if (customerCart.getProduct().getAvailableQuantity() < customerCart.getQuantity()) {
                em.getTransaction().rollback();
                em.close();
                return false;
            }
            OrderDetails orderDetails = new OrderDetails();
            OrderDetailsId orderDetailsId = new OrderDetailsId();
            orderDetailsId.setOrderId(order.getId());
            orderDetailsId.setProductId(customerCart.getId().getProductId());
            orderDetails.setId(orderDetailsId);
            orderDetails.setOrder(order);
            orderDetails.setProduct(customerCart.getProduct());
            orderDetails.setQuantity(customerCart.getQuantity());
            orderDetailsRepository.create(em, orderDetails);
            customerCartRepository.delete(em, customerCart);
            productRepository.getProduct(em, customerCart.getProduct().getId()).setAvailableQuantity(customerCart.getProduct().getAvailableQuantity() - customerCart.getQuantity());
        }
        customerRepository.update(em, customer);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    private Customer getCustomer(EntityManager em, int customerId) {
        CustomerRepository customerRepository = new CustomerRepository();
        return customerRepository.read(em, customerId);
    }

    private ArrayList<CustomerCart> getCustomerCart(EntityManager em, int customerId) {
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        return customerCartRepository.getCustomerCart(em, customerId);
    }

    private Order createOrder(Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        return order;
    }
}
