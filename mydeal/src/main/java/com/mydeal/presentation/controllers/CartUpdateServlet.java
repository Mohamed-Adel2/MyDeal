package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CartModel;
import com.mydeal.domain.models.OfflineCartModel;
import com.mydeal.domain.services.CustomerCartService;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

@WebServlet("/cart/update")
public class CartUpdateServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String operation = request.getParameter("operation");
        if (request.getSession(false) == null) {
            updateTempCart(request, response, operation);
        } else
            updateCustomerCart(request, response, operation);
    }

    private void updateTempCart(HttpServletRequest request, HttpServletResponse response, String operation) throws IOException {
        CartModel cart = extractCartItem(request);
        OfflineCartModel offlineCartModel = new OfflineCartModel();
        Cookie cookie = getCartCookie(request);
        if (cookie != null) {
            offlineCartModel = new Gson().fromJson(new String(Base64.getDecoder().decode(cookie.getValue())), OfflineCartModel.class);
        }
        if (operation.equals("update")) {
            if (!checkProductAvailability(cart.getProductId(), cart.getQuantity()) || cart.getQuantity() < 0) {
                response.getWriter().write(new Gson().toJson("failure"));
                return;
            }
            updateOfflineCart(offlineCartModel, cart);
        } else {
            deleteFromOfflineCart(new OfflineCartModel(), cart.getProductId());
        }
        Cookie newCookie = new Cookie("cart", Base64.getEncoder().encodeToString(new Gson().toJson(offlineCartModel).getBytes()));
        newCookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(newCookie);
        response.getWriter().write(new Gson().toJson("success"));
    }

    private void updateCustomerCart(HttpServletRequest request, HttpServletResponse response, String operation) throws IOException {
        CartModel cart = extractCartItem(request);
        if (!checkProductAvailability(cart.getProductId(), cart.getQuantity()) || cart.getQuantity() < 0) {
            response.getWriter().write(new Gson().toJson("failure"));
            return;
        }
        CustomerCartService customerCartService = new CustomerCartService();
        if (operation.equals("update")) {
            customerCartService.updateProductQuantity(cart);
        } else {
            customerCartService.removeProductFromCart(cart);
        }
        response.getWriter().write(new Gson().toJson("success"));
    }

    private CartModel extractCartItem(HttpServletRequest request) {
        CartModel cart = new CartModel();
        if (request.getSession(false) != null)
            cart.setCustomerId(((Customer) request.getSession(false).getAttribute("user")).getId());
        cart.setProductId(Integer.parseInt(request.getParameter("productId")));
        cart.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        return cart;
    }

    private boolean checkProductAvailability(int productId, int quantity) {
        ProductService productService = new ProductService();
        return productService.getProductQuantity(productId) >= quantity;
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

    private void updateOfflineCart(OfflineCartModel offlineCartModel, CartModel cart) {
        for (CartModel cartModel : offlineCartModel.getCartItems()) {
            if (cartModel.getProductId() == cart.getProductId()) {
                cartModel.setQuantity(cart.getQuantity());
                return;
            }
        }
    }

    private void deleteFromOfflineCart(OfflineCartModel offlineCartModel, int productId) {
        for (CartModel cartModel : offlineCartModel.getCartItems()) {
            if (cartModel.getProductId() == productId) {
                offlineCartModel.getCartItems().remove(cartModel);
                return;
            }
        }
    }
}
