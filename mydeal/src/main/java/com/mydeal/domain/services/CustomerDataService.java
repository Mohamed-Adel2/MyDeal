package com.mydeal.domain.services;

import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;

public class CustomerDataService {
    public int getCustomerBalance(int customerId) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        int ret = customerRepository.getCustomerBalance(em, customerId);
        em.close();
        return ret;
    }
}
