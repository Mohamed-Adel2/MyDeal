package com.mydeal.repository;

import com.mydeal.domain.entities.Order;
import jakarta.persistence.EntityManager;

public class OrderRepository extends CrudRepository<Order>{
    public OrderRepository() {

        setEntityClass(Order.class);
    }
}
