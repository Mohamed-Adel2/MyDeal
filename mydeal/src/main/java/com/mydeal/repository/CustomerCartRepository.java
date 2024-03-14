package com.mydeal.repository;

import com.mydeal.domain.entities.CustomerCart;
import jakarta.persistence.EntityManager;

public class CustomerCartRepository extends CrudRepository<CustomerCart> {
    public CustomerCartRepository() {

        setEntityClass(CustomerCart.class);
    }
}
