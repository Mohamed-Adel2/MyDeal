package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CheckStatusServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) == null && !getCartCookie(request)) {
            response.getWriter().write(new Gson().toJson("false"));
        } else {
            response.getWriter().write(new Gson().toJson("true"));
        }
    }

    private boolean getCartCookie(HttpServletRequest request) {
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
