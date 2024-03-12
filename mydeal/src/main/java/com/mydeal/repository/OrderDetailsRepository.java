package com.mydeal.repository;

import com.mydeal.domain.entities.OrderDetails;

public class OrderDetailsRepository extends CrudRepository<OrderDetails>{
    public OrderDetailsRepository() {
        setEntityClass(OrderDetails.class);
    }
}
