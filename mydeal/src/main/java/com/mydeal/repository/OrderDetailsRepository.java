package com.mydeal.repository;

import com.mydeal.domain.entities.OrderDetails;
import jakarta.persistence.EntityManager;

public class OrderDetailsRepository extends CrudRepository<OrderDetails>{
    public OrderDetailsRepository(EntityManager em) {
        super(em);
        setEntityClass(OrderDetails.class);
    }
}
