package com.mydeal.domain.services;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class CustomerDataService {
    public double getCustomerBalance(int customerId) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        double ret = customerRepository.getCustomerBalance(em, customerId);
        em.close();
        return ret;
    }

    public List<CustomerDataModel> getAllCustomers(String email, int startIdx, int limit) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        List<Customer> ret = customerRepository.getAllCustomer(em, email, startIdx, limit);
        List<CustomerDataModel> customersDataModels = new ArrayList<>();
        ret.forEach(customer -> customersDataModels.add(CustomerMapping.convertEntityToModel(customer)));
        em.close();
        return customersDataModels;
    }

    public Customer getCustomerById(int customerId) {
        CustomerRepository customerRepository = new CustomerRepository();
        EntityManager em = JpaUtil.createEntityManager();
        Customer customer = customerRepository.read(em, customerId);
        em.close();
        return customer;
    }
}
