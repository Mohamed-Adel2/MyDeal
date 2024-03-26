package com.mydeal.domain.services;

import com.mydeal.domain.util.JpaUtil;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;

public class CustomerDataValidatorService {
    public boolean validateEmail(String email) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        return customerRepository.checkFieldAvailability(em, RequestKey.RQ_CustomerEmail, email) == null;
    }

    public boolean validatePhone(String phone) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        return customerRepository.checkFieldAvailability(em, "phoneNumber", phone) == null;
    }
}
