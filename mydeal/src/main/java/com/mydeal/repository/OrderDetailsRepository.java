package com.mydeal.repository;

import com.mydeal.domain.entities.Order;
import com.mydeal.domain.entities.OrderDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderDetailsRepository extends CrudRepository<OrderDetails> {
    public OrderDetailsRepository() {
        setEntityClass(OrderDetails.class);
    }

    public List<OrderDetails> getOrderDetails(EntityManager em, int orderId) {
        System.out.println("orderId: " + orderId);
        TypedQuery<OrderDetails> query = em.createQuery("SELECT od FROM OrderDetails od WHERE od.id.orderId= :orderId", OrderDetails.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
