package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydeal.domain.models.FilterModel;
import com.mydeal.domain.models.ProductDataModel;
import com.mydeal.domain.services.CategoryService;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AdminProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterModel filter = extractDataFromRequest(req);
        CategoryService categoryService = new CategoryService();
        if (filter.getCategory().equals("All"))
            filter.setCategoryId(0);
        else
            filter.setCategoryId(categoryService.getCategoryId(filter.getCategory()));
        ProductService productService = new ProductService();
        List<ProductDataModel> products = productService.getAllProducts(filter);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        String jsonProducts = gson.toJson(products);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonProducts);
    }
    private FilterModel extractDataFromRequest(HttpServletRequest req) {
        FilterModel filterModel = new FilterModel();
        filterModel.setCategory(req.getParameter("category"));
        filterModel.setMinPrice(Double.parseDouble(req.getParameter("minPrice")));
        filterModel.setMaxPrice(Double.parseDouble(req.getParameter("maxPrice")));
        filterModel.setSearchKey(req.getParameter("searchKey"));
        filterModel.setStartIdx(Integer.parseInt(req.getParameter("startIdx")));
        filterModel.setLimit(Integer.parseInt(req.getParameter("limit")));
        return filterModel;
    }
}
