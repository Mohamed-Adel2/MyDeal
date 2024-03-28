package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.OrderCustomerMapping;
import com.mydeal.domain.models.CustomerOrderModel;
import com.mydeal.domain.models.OrderItemModel;
import com.mydeal.domain.services.CustomerDataService;
import com.mydeal.domain.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class OrderItemsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("orderId") == null) {
            response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson("failure").getBytes()));
            return;
        }
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderService orderService = new OrderService();
        List<Object> ret = new ArrayList<>();
        List<OrderItemModel> orderItems = orderService.getOrderDetails(orderId);
        Customer customer;
        if (request.getSession().getAttribute("user") != null) {
            customer = (Customer) request.getSession().getAttribute("user");
            if ((int) customer.getId() != orderItems.getFirst().getCustomerId()) {
                response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson("failure").getBytes()));
                return;
            }
        } else {
            CustomerDataService customerDataService = new CustomerDataService();
            customer = customerDataService.getCustomerById(orderItems.getFirst().getCustomerId());
        }
        CustomerOrderModel customerOrderModel = OrderCustomerMapping.EntityToModel(customer);
        ret.add(customerOrderModel);
        ret.add(orderItems);
        response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson(ret).getBytes()));
    }
}
