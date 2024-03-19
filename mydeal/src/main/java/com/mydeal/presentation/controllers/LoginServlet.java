package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.services.CustomerLoginService;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.presentation.utils.ClientJSAlert;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.util.Optional;


//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // TODO: handle cookies and load cart from cookies

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(false) != null) {
            resp.sendRedirect("index.html");
        } else
            resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(RequestKey.RQ_CustomerEmail);
        String password = req.getParameter(RequestKey.RQ_CustomerPassword);
        CustomerLoginService customerLoginService = new CustomerLoginService();
        Optional<Customer> loggedCustomer = Optional.ofNullable(customerLoginService.login(email, password));
        if (loggedCustomer.isPresent()) {
            resp.setContentType("application/json"); // Set content type to JSON
            resp.setCharacterEncoding("UTF-8");
            Customer customer = loggedCustomer.get();
            // Initialize the address association explicitly
            // Hibernate.initialize(customer.getAddress());
            String jsonCustomer = new Gson().toJson(CustomerMapping.convertEntityToModel(customer));
            req.getSession(true).setAttribute("user", customer);
            // Set JSON string as response content
            resp.getWriter().write(jsonCustomer);
        } else {
            resp.setContentType("text/html");
            ClientJSAlert.showAlert(resp, "Invalid Email or Password");
            resp.sendRedirect("login.html");
        }
    }
}
