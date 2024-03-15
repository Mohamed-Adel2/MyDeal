package com.mydeal.presentation.controllers;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.services.AddressService;
import com.mydeal.domain.services.CustomerUpdateInfoService;
import com.mydeal.presentation.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile")
public class CustomerProfileServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) == null)
            response.sendRedirect("login.html");
        else
            response.sendRedirect("login.html");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) == null) {
            response.sendRedirect("login.html");
            return;
        }
        Customer customer = (Customer) request.getSession().getAttribute("user");
        Utils.setCustomerAttributes(customer, request);
        AddressService addressService = new AddressService();
        CustomerUpdateInfoService customerUpdateInfoService = new CustomerUpdateInfoService();
        addressService.updateAddress(customer.getAddress());
        customerUpdateInfoService.updateCustomerInfo(customer);
        response.sendRedirect("index.html");
    }
}
