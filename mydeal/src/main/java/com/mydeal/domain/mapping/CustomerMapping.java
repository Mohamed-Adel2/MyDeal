package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerDataModel;

import java.time.LocalDate;
import java.util.Optional;

public class CustomerMapping {

    public static CustomerDataModel convertEntityToModel(Customer customer) {
        Optional<Integer> addressId = Optional.ofNullable(customer.getAddress().getId());

        CustomerDataModel customerDataModel = new CustomerDataModel();
        customerDataModel.setId(customer.getId());
        customerDataModel.setUserName(customer.getUsername());
        customerDataModel.setEmail(customer.getEmail());
        customerDataModel.setPhoneNumber(customer.getPhoneNumber());
        customerDataModel.setDob(customer.getDateOfBirth().toString());
        customerDataModel.setAddressId(addressId.isPresent() ? customer.getAddress().getId() : 0);
        customerDataModel.setAddressDataModel(addressId.isPresent() ? AddressMapping.convertEntityToModel(customer.getAddress()) : null);
        customerDataModel.setPassword(customer.getPassword());
        customerDataModel.setCreditLimit(customer.getCreditLimit());
        customerDataModel.setAddressDataModel(AddressMapping.convertEntityToModel(customer.getAddress()));
        return customerDataModel;
    }

    public static Customer convertModelToEntity(CustomerDataModel customerDataModel) {
        Customer customer = new Customer();
        customer.setId(customerDataModel.getId());
        customer.setUsername(customerDataModel.getUserName());
        customer.setEmail(customerDataModel.getEmail());
        customer.setPhoneNumber(customerDataModel.getPhoneNumber());
        customer.setDateOfBirth(LocalDate.parse(customerDataModel.getDob()));
        customer.setAddress(AddressMapping.convertModelToEntity(customerDataModel.getAddressDataModel()));
        customer.setPassword(customerDataModel.getPassword());
        customer.setCreditLimit(customerDataModel.getCreditLimit());
        customer.setAddress(customerDataModel.getAddressDataModel() != null ? AddressMapping.convertModelToEntity(customerDataModel.getAddressDataModel()) : null);
        return customer;
    }

}
