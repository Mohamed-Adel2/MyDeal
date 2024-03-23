package com.mydeal.presentation.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mydeal.domain.models.admin.AdminCategoryModel;
import com.mydeal.domain.services.CategoryService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminDeleteCategory extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson gson = new Gson();
        AdminCategoryModel deleteCategoryModel = gson.fromJson(req.getReader(), AdminCategoryModel.class);
        CategoryService categoryService = new CategoryService();
        boolean isDeleted = categoryService.deleteCategory(deleteCategoryModel.getCategoryName());
        JsonObject jsonResponse = new JsonObject();
        if(isDeleted){
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Category Deleted successfully.");
        }
        else{
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Failed to add Delete Category");
        }
    }
}
