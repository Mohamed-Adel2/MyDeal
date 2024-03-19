package com.mydeal.domain.services;

import com.mydeal.domain.entities.*;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerCartRepository;
import com.mydeal.repository.CustomerRepository;
import com.mydeal.repository.OrderDetailsRepository;
import com.mydeal.repository.OrderRepository;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;

public class CheckOutService {
    public void makeOrder(int customerId, double orderPrice) {
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        OrderDetailsRepository orderDetailsRepository = new OrderDetailsRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        OrderRepository orderRepository = new OrderRepository();
        EntityManager em = JpaUtil.createEntityManager();
        ArrayList<CustomerCart> cart = getCustomerCart(em, customerId);
        Customer customer = getCustomer(em, customerId);
        customer.setCreditLimit(customer.getCreditLimit() - orderPrice);
        Order order = createOrder(customer);
        em.getTransaction().begin();
        orderRepository.create(em, order);
        for (CustomerCart customerCart : cart) {
            OrderDetails orderDetails = new OrderDetails();
            OrderDetailsId orderDetailsId = new OrderDetailsId();
            orderDetailsId.setOrderId(order.getId());
            orderDetailsId.setProductId(customerCart.getId().getProductId());
            orderDetails.setId(orderDetailsId);
            orderDetailsRepository.create(em, orderDetails);
            customerCartRepository.delete(em, customerCart);
        }
        customerRepository.update(em, customer);
        em.getTransaction().commit();
        em.close();
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
