package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerInfoMapping;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.models.CustomerInfoModel;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomerDataInitializerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) != null) {
            Customer customer = (Customer) request.getSession().getAttribute("user");
            CustomerInfoModel customerModel = CustomerInfoMapping.convertEntityToModel(customer);
            response.getWriter().write(new Gson().toJson(customerModel));
        } else
            response.getWriter().write(new Gson().toJson("notAuthorized"));
    }
}
