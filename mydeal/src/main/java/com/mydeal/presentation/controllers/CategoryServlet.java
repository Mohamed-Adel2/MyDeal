package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydeal.domain.services.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CategoryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        List<String> categories = categoryService.getAllCategories();
        System.out.println(categories);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        String jsonCategories = gson.toJson(categories);
        System.out.println(jsonCategories);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonCategories);
    }
}
