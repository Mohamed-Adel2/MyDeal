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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    // TODO: handle adding old quantity


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CartModel cart = extractCartItem(request);
        if (!checkProductAvailability(cart.getProductId(), cart.getQuantity())) {
            writeStatusToResponse(response, "invalid");
            return;
        } else {
            writeStatusToResponse(response, "valid");
        }
        if (request.getSession(false) == null) {
            OfflineCartModel offlineCartModel = new OfflineCartModel();
            Cookie cookie = getCartCookie(request);
            if (cookie != null) {
                offlineCartModel = new Gson().fromJson(new String(Base64.getDecoder().decode(cookie.getValue())), OfflineCartModel.class);
            }
            updateOfflineCart(offlineCartModel, cart);
            Cookie newCookie = new Cookie("cart", Base64.getEncoder().encodeToString(new Gson().toJson(offlineCartModel).getBytes()));
            newCookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(newCookie);
        } else {
            CustomerCartService customerCartService = new CustomerCartService();
            customerCartService.addProductToCart(cart);
        }
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

    private void writeStatusToResponse(HttpServletResponse response, String status) throws IOException {
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(status));
    }

    private void updateOfflineCart(OfflineCartModel offlineCartModel, CartModel cart) {
        for (CartModel cartModel : offlineCartModel.getCartItems()) {
            if (cartModel.getProductId() == cart.getProductId()) {
                cartModel.setQuantity(cartModel.getQuantity() + cart.getQuantity());
                return;
            }
        }
        offlineCartModel.getCartItems().add(cart);
    }
}
