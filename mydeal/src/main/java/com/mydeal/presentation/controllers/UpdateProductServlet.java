package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mydeal.domain.models.UpdateProductModel;
import com.mydeal.domain.services.ProductImageService;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class UpdateProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        UpdateProductModel updateProductModel = gson.fromJson(req.getReader(), UpdateProductModel.class);
        ProductService productService = new ProductService();
        ProductImageService productImageService = new ProductImageService();

        boolean dataUpdated = productService.updateProduct(updateProductModel);
        if (updateProductModel.getDeleted().length > 0) {
            productImageService.deletedImagesToProduct(updateProductModel);
        }
        if (updateProductModel.getAdded().length > 0) {
            productImageService.addNewImagesToProduct(updateProductModel);
        }
        JsonObject jsonResponse = new JsonObject();
        if (dataUpdated) {
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Data Updated successfully.");
        } else {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Failed To Updated.");
        }

        // Set response content type
        resp.setContentType("application/json");

        // Write response JSON to output stream
        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse.toString());
        }
    }
}
