package com.mydeal.presentation.controllers;

import com.mydeal.domain.services.CustomerDataValidatorService;
import com.mydeal.domain.util.RequestKey;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/validate")
public class CredentialValidatorServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Validation Request Received");
        String email = req.getParameter(RequestKey.RQ_CustomerEmail);
        String phoneNumber = req.getParameter(RequestKey.RQ_CustomerPhoneNumber);
        CustomerDataValidatorService customerDataValidatorService = new CustomerDataValidatorService();
        String ret = "";
        if (email != null) {
            ret = customerDataValidatorService.validateEmail(email) ? "valid" : "invalid";
        } else if (phoneNumber != null) {
            ret = customerDataValidatorService.validatePhone(phoneNumber) ? "valid" : "invalid";
        }
        // Set response content type to JSON
        resp.setContentType("application/json");
        // Set response character encoding (optional)
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(ret);
    }
}
