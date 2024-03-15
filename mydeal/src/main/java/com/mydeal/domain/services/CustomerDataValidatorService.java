package com.mydeal.domain.services;

import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;

public class CustomerDataValidatorService {
    public boolean validateEmail(String email) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        return customerRepository.checkFieldAvailability(em, "email", email) == null;
    }

    public boolean validatePhone(String phone) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        return customerRepository.checkFieldAvailability(em, "phone", phone) == null;
    }
}
