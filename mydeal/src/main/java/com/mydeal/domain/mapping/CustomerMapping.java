package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerDataModel;

public class CustomerMapping {

    public CustomerDataModel convertEntityToModel(Customer customer) {
        CustomerDataModel customerDataModel = new CustomerDataModel();
        customerDataModel.setId(customer.getId());
        customerDataModel.setUserName(customer.getUsername());
        customerDataModel.setEmail(customer.getEmail());
        customerDataModel.setPhoneNumber(customer.getPhoneNumber());
        customerDataModel.setDob(customer.getDateOfBirth().toString());
        customerDataModel.setAddressId(customer.getAddress().getId());
        customerDataModel.setPassword(customer.getPassword());
        customerDataModel.setCreditLimit(customer.getCreditLimit());
        return customerDataModel;
    }


}
