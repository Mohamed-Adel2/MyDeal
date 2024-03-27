package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Address;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.services.AddressService;
import com.mydeal.domain.services.CustomerRegistrationService;
import com.mydeal.presentation.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = new Customer();
        Utils.setCustomerAttributes(customer, req);
        try {
            CustomerRegistrationService customerRegistrationService = new CustomerRegistrationService();
            AddressService addressService = new AddressService();
            addressService.createAddress(customer.getAddress());
            customer.setIsAdmin(0);
            customerRegistrationService.register(customer);
            resp.getWriter().write(new Gson().toJson("success"));
        }
        catch (Exception e) {
            resp.getWriter().write(new Gson().toJson("failure"));
        }
    }
}