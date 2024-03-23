package com.mydeal.presentation.controllers.admin;

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
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("Id"));
        ProductService productService = new ProductService();
        boolean isDeleted = productService.deleteProduct(productId);
        System.out.println(isDeleted);
        PrintWriter wr = resp.getWriter();
        if (isDeleted) {
            wr.write("Product deleted successfully");
        } else {
            wr.write("Failed To deleted product");
        }
    }
}
