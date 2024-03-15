package com.mydeal.domain.services;

import com.mydeal.domain.entities.Address;
import com.mydeal.domain.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class AddressService {
    public void updateAddress(Address address) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        em.merge(address);
        em.getTransaction().commit();
    }

    public void createAddress(Address address) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
    }
}
