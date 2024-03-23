package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CartModel;
import com.mydeal.domain.models.OfflineCartModel;
import com.mydeal.domain.models.ProductDetailDataModel;
import com.mydeal.domain.services.CustomerCartService;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class CartItemsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(false) == null) {
            retrieveTempCart(req, resp);
        } else
            retrieveCustomerCart(req, resp);
    }

    private void retrieveTempCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OfflineCartModel offlineCartModel = new OfflineCartModel();
        Cookie cookie = getCartCookie(request);
        if (cookie != null) {
            offlineCartModel = new Gson().fromJson(new String(Base64.getDecoder().decode(cookie.getValue())), OfflineCartModel.class);
        }
        ProductService productService = new ProductService();
        ArrayList<ProductDetailDataModel> products = new ArrayList<>();
        for (CartModel cartModel : offlineCartModel.getCartItems()) {
            products.add(productService.getProduct(cartModel.getProductId()));
            products.getLast().setQuantity(cartModel.getQuantity());
        }
        response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson(products).getBytes()));
    }

    private void retrieveCustomerCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int customerId = ((Customer) request.getSession(false).getAttribute("user")).getId();
        CustomerCartService customerCartService = new CustomerCartService();
        ArrayList<CartModel> cart = customerCartService.getCustomerCartItems(customerId);
        ProductService productService = new ProductService();
        ArrayList<ProductDetailDataModel> products = new ArrayList<>();
        for (CartModel cartModel : cart) {
            products.add(productService.getProduct(cartModel.getProductId()));
            products.get(products.size() - 1).setQuantity(cartModel.getQuantity());
        }
        response.getWriter().write(Base64.getEncoder().encodeToString(new Gson().toJson(products).getBytes()));
    }

    private Cookie getCartCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
