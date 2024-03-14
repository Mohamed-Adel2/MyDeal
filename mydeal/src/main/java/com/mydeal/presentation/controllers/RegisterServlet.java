package com.mydeal.presentation.controllers;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.repository.CustomerRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

//@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @
    @ -21,17+23,19@@

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws Se

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Post in Register Called");
        System.out.println("name in req : " + req.getParameter(RequestKey.RQ_CustomerUserName));
        System.out.println("email in req : " + req.getParameter(RequestKey.RQ_CustomerEmail));
        System.out.println("password in req : " + req.getParameter(RequestKey.RQ_CustomerPassword));
        CustomerDataModel.getInstance().setUserName(req.getParameter(RequestKey.RQ_CustomerUserName))
                .setEmail(req.getParameter(RequestKey.RQ_CustomerEmail))
                .setPhoneNumber(req.getParameter(RequestKey.RQ_CustomerPhoneNumber))
                .setDob(req.getParameter(RequestKey.RQ_CustomerDOB))
                .setPassword(req.getParameter(RequestKey.RQ_CustomerPassword))
                .setAddress(req.getParameter(RequestKey.RQ_CustomerAddress))
                .setCreditLimit(BigDecimal.ZERO);
        System.out.println(CustomerDataModel.getInstance().toString());
        CustomerRepository customerRepository = new CustomerRepository();
        customerRepository.setEntityClass(Customer.class);
        customerRepository.create(CustomerDataModel.getInstance().getEm(), CustomerDataModel.getInstance().convertToCustomerEntity());
    }

}
