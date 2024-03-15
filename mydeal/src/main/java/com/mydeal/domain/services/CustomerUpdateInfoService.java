package com.mydeal.domain.services;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;

public class CustomerUpdateInfoService {
    // TODO: update address info
    public void updateCustomerInfo(Customer customer) {
        EntityManager em = JpaUtil.createEntityManager();
        CustomerRepository customerRepository = new CustomerRepository();
        em.getTransaction().begin();
        customerRepository.update(em, customer);
        em.getTransaction().commit();
        em.close();
    }
}
