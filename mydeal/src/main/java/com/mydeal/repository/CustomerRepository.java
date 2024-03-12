package com.mydeal.repository;

import com.mydeal.domain.entities.Customer;

public class CustomerRepository extends CrudRepository<Customer> {
    public CustomerRepository() {
        setEntityClass(Customer.class);
    }
}
