package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydeal.domain.models.ProductDataModel;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int start = Integer.parseInt(req.getParameter("startIdx"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        ProductService productService = new ProductService();
        List<ProductDataModel> products = productService.getAllProducts(start, limit);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        String jsonProducts = gson.toJson(products);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonProducts);
    }

}
