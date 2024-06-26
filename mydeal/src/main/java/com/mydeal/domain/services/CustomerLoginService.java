package com.mydeal.domain.services;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;

public class CustomerLoginService {
    public Customer login(String email, String password) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        Customer customer = customerRepository.getCustomerByEmailAndPassword(em, email, password);
        em.close();
        return customer;
    }
    public boolean checkAdmin(String email){
        EntityManager em = JpaUtil.createEntityManager();
        CustomerRepository customerRepository = new CustomerRepository();
        boolean isAdmin = customerRepository.checkAdmin(em,email);
        em.close();
        return  isAdmin;

    }
}
