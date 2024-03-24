package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydeal.domain.models.ProductDetailDataModel;
import com.mydeal.domain.services.CategoryService;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ProductDetailServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("Id"));
        ProductService productService = new ProductService();
        ProductDetailDataModel productDetailDataModel = productService.getProduct(productId);
        if (productDetailDataModel == null) {
            resp.getWriter().write(new Gson().toJson("notFound"));
        }
        else if (productDetailDataModel.getIsRemoved() == 1) {
            resp.getWriter().write(new Gson().toJson("removed"));
        } else {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            String jsonProducts = gson.toJson(productDetailDataModel);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonProducts);
        }
    }
}