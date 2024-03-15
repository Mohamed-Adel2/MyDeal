package com.mydeal.presentation.controllers;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.services.CustomerRegistrationService;
import com.mydeal.presentation.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = new Customer();
        Utils.setCustomerAttributes(customer, req);

        // TODO: validation on data

        CustomerRegistrationService customerRegistrationService = new CustomerRegistrationService();
        customerRegistrationService.register(customer);
        /*AddressService addressService = new AddressService();


        // Associate the persisted Address entity with the CustomerDataModel
        customerDataModel.setAddressId(addressEntity.getId());

        // Convert CustomerDataModel to Customer entity
        Customer customerEntity = CustomerMapping.convertModelToEntity(customerDataModel);

        // Set the Address entity to the Customer entity
        customerEntity.setAddress(addressEntity);

        // Persist the Customer entity
        CustomerRepository customerRepository = new CustomerRepository();
        customerRepository.setEntityClass(Customer.class);
        customerRepository.create(em, customerEntity);

        // Commit and close EntityManagers
        em.close();
        addressEm.close();*/
        System.out.println("here");
        resp.sendRedirect("login.html");
    }

}