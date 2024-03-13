package com.mydeal.presentation.controllers;

import com.mydeal.domain.models.UserDataModel;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.presentation.controllers.frontcontroller.Controller;
import com.mydeal.presentation.controllers.frontcontroller.viewresolve.ViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/register")
public class RegisterServlet extends HttpServlet implements Controller {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Get Register Called");
    }

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
    }

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
