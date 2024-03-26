package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerOrderModel;

public class OrderCustomerMapping {
    public static CustomerOrderModel EntityToModel(Customer customer) {
        CustomerOrderModel customerOrderModel = new CustomerOrderModel();
        customerOrderModel.setName(customer.getUsername());
        customerOrderModel.setPhone(customer.getPhoneNumber());
        customerOrderModel.setAddress(AddressOrderMapping.EntityToModel(customer.getAddress()));
        return customerOrderModel;
    }
}
