package com.mydeal.repository;

import com.mydeal.domain.entities.CustomerCart;
import com.mydeal.domain.models.CartModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;

public class CustomerCartRepository extends CrudRepository<CustomerCart> {
    public CustomerCartRepository() {
        setEntityClass(CustomerCart.class);
    }

    public ArrayList<CustomerCart> getCustomerCart(EntityManager em, int customerId) {
        return (ArrayList<CustomerCart>) em.createQuery("SELECT c FROM CustomerCart c WHERE c.id.customerId = :customerId")
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public int getCustomerCartPrice(EntityManager em, int customerId) {
        TypedQuery<Integer> query = em.createQuery("SELECT SUM(c.quantity * p.price) FROM CustomerCart c JOIN Product p ON c.id.productId = p.id WHERE c.id.customerId = :customerId", Integer.class);
        query.setParameter("customerId", customerId);
        return query.getSingleResult();
    }
}
