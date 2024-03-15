package com.mydeal.presentation.controllers;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.services.CustomerLoginService;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.presentation.utils.ClientJSAlert;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;


//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // TODO: handle cookies and load cart from cookies

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(false) != null)
            resp.sendRedirect("index.html");
        else
            resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String email = req.getParameter(RequestKey.RQ_CustomerEmail);
        String password = req.getParameter(RequestKey.RQ_CustomerPassword);

        CustomerLoginService customerLoginService = new CustomerLoginService();
        Optional<Customer> loggedCustomer = Optional.ofNullable(customerLoginService.login(email, password));
        if (loggedCustomer.isPresent()) {
            Customer customer = loggedCustomer.get();
            req.getSession(true).setAttribute("user", customer);
            resp.sendRedirect("index.html");
        } else {
            resp.setContentType("text/html");
            ClientJSAlert.showAlert(resp, "Invalid Email or Password");
            resp.sendRedirect("login.html");
        }
    }
}
