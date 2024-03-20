package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UpdateInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("Update user info servlet called.");
//        Customer customer = (Customer) req.getSession().getAttribute("user");
//        if (customer == null) {
//            System.out.println("User not found in session.");
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found in session.");
//            return;
//        }
//        System.out.println(customer.getEmail());
//        CustomerDataModel customerDataModel = CustomerMapping.convertEntityToModel(customer);
//        Gson gson = new Gson();
//        String json = gson.toJson(customerDataModel);
//        resp.setCharacterEncoding("UTF-8");
//        resp.setContentType("application/json");
//        resp.addHeader("Access-Control-Allow-Origin", "*");
//        resp.getWriter().write(json);
//        resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
