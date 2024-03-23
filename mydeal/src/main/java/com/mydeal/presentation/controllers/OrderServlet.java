package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.OrderModel;
import com.mydeal.domain.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
            response.getWriter().write(new Gson().toJson("unauthorized"));
            return;
        }
        Integer customerId = ((Customer) request.getSession(false).getAttribute("user")).getId();
        Integer startIdx = Integer.parseInt(request.getParameter("startIdx"));
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        OrderService orderService = new OrderService();
        List<OrderModel> orderModels = orderService.getCustomerOrders(customerId, startIdx, limit);
        response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson(orderModels).getBytes()));
    }
}
