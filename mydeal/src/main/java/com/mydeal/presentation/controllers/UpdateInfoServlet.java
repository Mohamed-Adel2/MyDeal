package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.services.AddressService;
import com.mydeal.domain.services.CustomerUpdateInfoService;
import com.mydeal.presentation.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UpdateInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get");
        if (request.getSession(false) == null) {
            response.getWriter().write(new Gson().toJson("failure"));
        } else {
            System.out.println("sl1");
            Customer customer = (Customer) request.getSession(false).getAttribute("user");
            Utils.setCustomerAttributes(customer, request);
            System.out.println("sl2");
            AddressService addressService = new AddressService();
            CustomerUpdateInfoService customerUpdateInfoService = new CustomerUpdateInfoService();
            addressService.updateAddress(customer.getAddress());
            customerUpdateInfoService.updateCustomerInfo(customer);
            response.getWriter().write(new Gson().toJson("success"));
        }
    }
}
