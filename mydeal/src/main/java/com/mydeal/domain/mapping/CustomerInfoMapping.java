package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerInfoModel;

public class CustomerInfoMapping {
    public static CustomerInfoModel convertEntityToModel(Customer customer) {
        CustomerInfoModel customerInfoModel = new CustomerInfoModel();
        customerInfoModel.setUserName(customer.getUsername());
        customerInfoModel.setEmail(customer.getEmail());
        customerInfoModel.setPhoneNumber(customer.getPhoneNumber());
        customerInfoModel.setDob(customer.getDateOfBirth().toString());
        customerInfoModel.setPassword(customer.getPassword());
        customerInfoModel.setCreditLimit(customer.getCreditLimit().toString());
        customerInfoModel.setStreet(customer.getAddress().getStreet());
        customerInfoModel.setCity(customer.getAddress().getCity());
        customerInfoModel.setApartment(customer.getAddress().getApartment());
        return customerInfoModel;
    }
}
