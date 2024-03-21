package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.services.CheckOutService;
import com.mydeal.domain.services.CustomerCartService;
import com.mydeal.domain.services.CustomerDataService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class CheckOutServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) != null && request.getSession(false).getAttribute("user") == null) {
            response.getWriter().write(new Gson().toJson("notAuthorized"));
            return;
        }
        int customerId = ((Customer) request.getSession(false).getAttribute("user")).getId();
        double customerBalance = getCustomerBalance(customerId);
        double cartItemsPrice = getCartItemsPrice(customerId);
        if (customerBalance >= cartItemsPrice) {
            CheckOutService checkOutService = new CheckOutService();
            if (checkOutService.makeOrder(customerId, cartItemsPrice))
                response.getWriter().write(new Gson().toJson("success"));
            else
                response.getWriter().write(new Gson().toJson("quantityFailure"));
        } else {
            response.getWriter().write(new Gson().toJson("balanceFailure"));
        }
    }

    private double getCustomerBalance(int customerId) {
        CustomerDataService customerDataService = new CustomerDataService();
        return customerDataService.getCustomerBalance(customerId);
    }

    private double getCartItemsPrice(int customerId) {
        CustomerCartService customerCartService = new CustomerCartService();
        return customerCartService.getCartItemsPrice(customerId);
    }

}
