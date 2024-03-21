package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.models.OrderItemModel;
import com.mydeal.domain.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class OrderItemsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("orderId") == null) {
            response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson("failure").getBytes()));
            return;
        }
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderService orderService = new OrderService();
        List<OrderItemModel> orderItems = orderService.getOrderDetails(orderId);
        System.out.println("Size of orderItems: " + orderItems.size());
        response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson(orderItems).getBytes()));
    }
}
