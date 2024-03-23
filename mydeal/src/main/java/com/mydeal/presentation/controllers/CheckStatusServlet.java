package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CheckStatusServlet extends HttpServlet {
    // TODO: check if user is logged in after these steps
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) == null && !getAuthenticationCookie(request)) {
            response.getWriter().write(new Gson().toJson("notAuthorized"));
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
}
