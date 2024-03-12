package com.mydeal.presentation.controllers;

import com.mydeal.domain.models.UserDataModel;
import com.mydeal.domain.util.RequestKey;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataModel userDataModel = new UserDataModel();
        userDataModel.setUserName(req.getParameter(RequestKey.RQ_UserName))
                .setEmail(req.getParameter(RequestKey.RQ_Email))
                .setPhoneNumber(req.getParameter(RequestKey.RQ_PhoneNumber))
                .setDob(req.getParameter(RequestKey.RQ_DOB))
                .setPassword(req.getParameter(RequestKey.RQ_Password))
                .setAddress(req.getParameter(RequestKey.RQ_Address))
                .setIsAdmin(false);
        System.out.println(userDataModel.toString());
    }
}
