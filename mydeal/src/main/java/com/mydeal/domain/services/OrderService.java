package com.mydeal.domain.services;

import com.mydeal.domain.entities.Order;
import com.mydeal.domain.entities.OrderDetails;
import com.mydeal.domain.mapping.OrderDetailsMapping;
import com.mydeal.domain.mapping.OrderMapping;
import com.mydeal.domain.models.OrderItemModel;
import com.mydeal.domain.models.OrderModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.OrderDetailsRepository;
import com.mydeal.repository.OrderRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public List<OrderModel> getCustomerOrders(Integer customerId, Integer startIdx, Integer limit) {
        OrderRepository orderRepository = new OrderRepository();
        EntityManager em = JpaUtil.createEntityManager();
        List<Order> orders = orderRepository.getCustomerOrders(em, customerId, startIdx, limit);
        List<OrderModel> orderModels = new ArrayList<>();
        for (Order order : orders) {
            orderModels.add(OrderMapping.mapOrderToOrderModel(order));
        }
        em.close();
        return orderModels;
    }

    public List<OrderItemModel> getOrderDetails(Integer orderId) {
        OrderDetailsRepository orderRepository = new OrderDetailsRepository();
        EntityManager em = JpaUtil.createEntityManager();
        List<OrderItemModel> orderItems = new ArrayList<>();
        List<OrderDetails> orderDetails = orderRepository.getOrderDetails(em, orderId);
        for (OrderDetails od : orderDetails) {
            orderItems.add(OrderDetailsMapping.convertEntityToModel(od));
        }
        em.close();
        return orderItems;
    }
}
