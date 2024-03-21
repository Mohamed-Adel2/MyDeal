package com.mydeal.domain.services;

import com.mydeal.domain.entities.Order;
import com.mydeal.domain.mapping.OrderMapping;
import com.mydeal.domain.models.OrderModel;
import com.mydeal.domain.util.JpaUtil;
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
        return orderModels;
    }
}
