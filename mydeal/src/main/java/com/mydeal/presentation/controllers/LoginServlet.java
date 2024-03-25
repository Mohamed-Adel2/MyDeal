package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.AuthenticationModel;
import com.mydeal.domain.models.OfflineCartModel;
import com.mydeal.domain.services.CustomerCartService;
import com.mydeal.domain.services.CustomerLoginService;
import com.mydeal.domain.util.RequestKey;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;


public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(false) != null) {
            resp.getWriter().write(new Gson().toJson("valid"));
        } else {
            CustomerLoginService customerLoginService = new CustomerLoginService();
            Customer customer = customerLoginService.login(req.getParameter(RequestKey.RQ_CustomerEmail), req.getParameter(RequestKey.RQ_CustomerPassword));
            /**
             * auth cookie is used to handle remember me option .
             */
            if (getCookie(req, "auth") != null) {
                if (customer.getIsAdmin() == 1) {
                    req.getSession(true).setAttribute("admin", customer);
                    resp.getWriter().write(new Gson().toJson("Admin"));
                } else {
                    req.getSession(true).setAttribute("user", customer);
                    resp.getWriter().write(new Gson().toJson("valid"));
                }
                return;
            }
            if (customer == null) {
                resp.getWriter().write(new Gson().toJson("invalid"));
            } else {
                if (customer.getIsAdmin() == 1)
                    req.getSession(true).setAttribute("admin", customer);
                else {
                    req.getSession(true).setAttribute("user", customer);
                    updateCart(getCookie(req, "cart"), customer.getId());
                }
                if (req.getParameter("rememberMe") != null && req.getParameter("rememberMe").equals("true")) {
                    int isAdmin = customer.getIsAdmin();
                    AuthenticationModel authenticationModel = new AuthenticationModel(customer.getEmail(), customer.getPassword(), isAdmin);
                    Cookie authCookie = new Cookie("auth", Base64.getEncoder().encodeToString(new Gson().toJson(authenticationModel).getBytes()));
                    authCookie.setMaxAge(60 * 60 * 24 * 30);
                    resp.addCookie(authCookie);
                }
                if (customer.getIsAdmin() == 1) {
                    resp.getWriter().write(new Gson().toJson("Admin"));
                } else {
                    resp.getWriter().write(new Gson().toJson("valid"));
                }
            }
        }
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private void updateCart(Cookie cookie, int customerId) {
        if (cookie == null)
            return;
        OfflineCartModel offlineCartModel = new Gson().fromJson(new String(Base64.getDecoder().decode(cookie.getValue())), OfflineCartModel.class);
        CustomerCartService customerCartService = new CustomerCartService();
        customerCartService.addCartItems(offlineCartModel.getCartItems(), customerId);
        cookie.setMaxAge(0);
    }
}
