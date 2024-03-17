package com.mydeal.repository;

import com.mydeal.domain.entities.CustomerCart;
import com.mydeal.domain.models.CartModel;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;

public class CustomerCartRepository extends CrudRepository<CustomerCart> {
    public CustomerCartRepository() {
        setEntityClass(CustomerCart.class);
    }

    public ArrayList<CustomerCart> getCustomerCart(EntityManager em, int customerId) {
        return (ArrayList<CustomerCart>) em.createQuery("SELECT c FROM CustomerCart c WHERE c.customerId = :customerId")
                .setParameter("customerId", customerId)
                .getResultList();
    }
}
