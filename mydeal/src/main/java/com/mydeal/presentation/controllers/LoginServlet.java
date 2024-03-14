package com.mydeal.presentation.controllers;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Get Login Called ");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(RequestKey.RQ_CustomerEmail);
        String password = req.getParameter(RequestKey.RQ_CustomerPassword);
        System.out.println("Email is : " + email + " password is : " + password);
        CustomerRepository customerRepository = new CustomerRepository();
        System.out.println("Customer repo created ");
        CustomerDataModel customerDataModel = customerRepository.getCustomerByEmailAndPassword(email, password);
        System.out.println(customerDataModel.toString());
    }
}
