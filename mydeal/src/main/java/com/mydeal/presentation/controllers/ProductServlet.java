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
       //make request to database to get Products
        ProductService productService = new ProductService();
        System.out.println("data not cached");
        System.out.println("data changed");
       List<ProductDataModel> products =productService.getAllProducts();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        String jsonProducts = gson.toJson(products);
        System.out.println("data fetched");
        resp.setContentType("application/json");
        resp.getWriter().write(jsonProducts);
    }

}
