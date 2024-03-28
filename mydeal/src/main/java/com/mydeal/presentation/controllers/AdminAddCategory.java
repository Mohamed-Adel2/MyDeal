package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mydeal.domain.models.AdminCategoryModel;
import com.mydeal.domain.services.CategoryService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AdminAddCategory extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson gson = new Gson();
        AdminCategoryModel addCategoryModel = gson.fromJson(req.getReader(), AdminCategoryModel.class);
        CategoryService categoryService = new CategoryService();
        int added = categoryService.AddCategory(addCategoryModel.getCategoryName());
        JsonObject jsonResponse = new JsonObject();
        if(added>0){
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Category added successfully.");
        }else if(added==-1){
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Category already exist");
        }
        else{
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Failed to add Add Category");
        }
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        out.println(jsonResponse.toString());
        out.close();
    }
}
