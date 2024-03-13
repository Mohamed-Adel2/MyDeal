package com.mydeal.repository;

import com.mydeal.domain.entities.Order;
import jakarta.persistence.EntityManager;

public class OrderRepository extends CrudRepository<Order>{
    public OrderRepository(EntityManager em) {
        super(em);
        setEntityClass(Order.class);
    }
}
