package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerDetail;

public class CustomerMap {
    private Customer customer;
    private CustomerDetail customerDetail;

    public CustomerDetail convertEntityToModel(Customer customer){
        this.customer = customer;
        customerDetail = new CustomerDetail();
        customerDetail.setId(customer.getId());
        customerDetail.setUserName(customer.getUsername());
        customerDetail.setPhoneNumber(customer.getPhoneNumber());
        customerDetail.setDateOfBirth(customer.getDateOfBirth());
        customerDetail.setPhoneNumber(customer.getPhoneNumber());
        customerDetail.setEmail(customer.getEmail());
        customerDetail.setCity(customer.getAddress().getCity());
        customerDetail.setStreet(customer.getAddress().getStreet());
        customerDetail.setApartment(customer.getAddress().getApartment());
        return customerDetail;
        //customerDetail.setOrders(customer.getOrders());
    }
}
