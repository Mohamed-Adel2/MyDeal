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

    public double getCustomerCartPrice(EntityManager em, int customerId) {
        TypedQuery<Double> query = em.createQuery("SELECT coalesce(SUM(c.quantity * p.price), 0) FROM CustomerCart c JOIN Product p ON c.id.productId = p.id WHERE c.id.customerId = :customerId", Double.class);
        query.setParameter("customerId", customerId);
        return query.getSingleResult();
    }

//    public void deleteProductFromCustomerCart(EntityManager em, int productId) {
//        em.createQuery("DELETE FROM CustomerCart c WHERE c.id.productId = :productId")
//                .setParameter("productId", productId)
//                .executeUpdate();
//    }

    public int deleteProductFromCustomerCart(EntityManager em, int productId, int customerId) {
        return em.createQuery("DELETE FROM CustomerCart c WHERE c.id.productId = :productId AND c.id.customerId = :customerId")
                .setParameter("productId", productId).setParameter("customerId", customerId)
                .executeUpdate();
    }
}
