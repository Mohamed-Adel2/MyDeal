package com.mydeal.domain.services;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;

public class CustomerRegistrationService {
    public void register(Customer customer) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        customerRepository.create(em, customer);
        em.getTransaction().commit();
        em.close();
    }
}
