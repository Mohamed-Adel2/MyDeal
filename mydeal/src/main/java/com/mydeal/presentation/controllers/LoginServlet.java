package com.mydeal.presentation.controllers;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.presentation.utils.ClientJSAlert;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;


//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Get Login Called ");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String email = req.getParameter(RequestKey.RQ_CustomerEmail);
        String password = req.getParameter(RequestKey.RQ_CustomerPassword);
        CustomerRepository customerRepository = new CustomerRepository();
        Optional<CustomerDataModel> loggedCustomerDataModel = Optional.ofNullable(customerRepository.getCustomerByEmailAndPassword(email, password));
        if (loggedCustomerDataModel.isPresent()) {
            // get the logged in customer data model
            CustomerDataModel customerDataModel = loggedCustomerDataModel.get();
            // set the logged in customer data model in the session
            req.getSession(true).setAttribute("customerDataModel", customerDataModel);
            // redirect to index.html
            // Set the content type before writing any content to the response
            resp.sendRedirect("index.html");
        } else {
            resp.setContentType("text/html");
            // Write the JavaScript alert directly to the response
            resp.getWriter().println("<script>alert('Invalid Email or Password');</script>");
            System.out.println("The customer not found");
            // this mean that the customer with these credentials does not exist
            ClientJSAlert.showAlert(resp, "Invalid Email or Password");
            resp.sendRedirect("login.html");
        }
    }
}
