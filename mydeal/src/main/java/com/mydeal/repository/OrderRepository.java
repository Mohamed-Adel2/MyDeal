package com.mydeal.repository;

import com.mydeal.domain.entities.Order;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.models.FilterModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderRepository extends CrudRepository<Order> {
    public OrderRepository() {
        setEntityClass(Order.class);
    }

    public List<Order> getCustomerOrders(EntityManager em, Integer customerId, Integer startIdx, Integer limit) {
        // order the results descending by order date
        TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.customer.id = :customerId order by o.orderDate desc, o.id desc", Order.class);
        query.setParameter("customerId", customerId);
        query.setFirstResult(startIdx);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
