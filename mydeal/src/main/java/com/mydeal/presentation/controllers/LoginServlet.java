package com.mydeal.presentation.controllers;

import com.mydeal.domain.models.UserDataModel;
import com.mydeal.domain.util.RequestKey;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(RequestKey.RQ_Email);
        String password = req.getParameter(RequestKey.RQ_Password);
        System.out.println("Email is : " + email + "password is : " + password);
        // TODO fetch credentials from data base , set the details to UserDataModel Obj then navigate to home page .
    }
}
