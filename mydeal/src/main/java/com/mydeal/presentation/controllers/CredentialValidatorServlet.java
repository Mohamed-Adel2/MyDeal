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
        String email = req.getParameter(RequestKey.RQ_CustomerEmail);
        String phoneNumber = req.getParameter(RequestKey.RQ_CustomerPhoneNumber);
        CustomerDataValidatorService customerDataValidatorService = new CustomerDataValidatorService();
        String ret = "invalid";
        if (email != null) {
            ret = customerDataValidatorService.validateEmail(email) ? "valid" : "invalid";
        } else if (phoneNumber != null) {
            ret = customerDataValidatorService.validatePhone(phoneNumber) ? "valid" : "invalid";
        }
        resp.getWriter().write(ret);
    }
}
