package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.AuthenticationModel;
import com.mydeal.domain.services.CustomerLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CheckStatusServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) == null && !getAuthenticationCookie(request)) {
            response.getWriter().write(new Gson().toJson("notAuthorized"));
        } else if (request.getSession(false) == null && getAuthenticationCookie(request)) {
            AuthenticationModel authenticationModel = new Gson().fromJson(new String(java.util.Base64.getDecoder().decode(getCookie(request).getValue())), AuthenticationModel.class);
            CustomerLoginService customerLoginService = new CustomerLoginService();
            Customer customer = customerLoginService.login(authenticationModel.getEmail(), authenticationModel.getPassword());
            if (customer == null) {
                response.getWriter().write(new Gson().toJson("notAuthorized"));
                return;
            }
            if (customer.getIsAdmin() == 1) {
                request.getSession(true).setAttribute("admin", customer);
                response.getWriter().write(new Gson().toJson("admin"));
            } else {
                request.getSession(true).setAttribute("user", customer);
                response.getWriter().write(new Gson().toJson("user"));
            }
        } else if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null) {
            response.getWriter().write(new Gson().toJson("user"));
        } else if (request.getSession(false) != null && request.getSession(false).getAttribute("admin") != null) {
            response.getWriter().write(new Gson().toJson("admin"));
        } else
            response.getWriter().write(new Gson().toJson("notAuthorized"));
    }

    private boolean getAuthenticationCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth")) {
                    return true;
                }
            }
        }
        return false;
    }

    private Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth")) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
