package com.mydeal.repository;

import com.mydeal.domain.entities.Customer;
import jakarta.persistence.EntityManager;

public class CustomerRepository extends CrudRepository<Customer> {
    public CustomerRepository() {

        setEntityClass(Customer.class);
    }
}
