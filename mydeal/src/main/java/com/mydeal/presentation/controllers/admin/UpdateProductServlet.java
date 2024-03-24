package com.mydeal.presentation.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mydeal.domain.models.ProductDataModel;
import com.mydeal.domain.models.admin.AddProductModel;
import com.mydeal.domain.models.admin.UpdateProductModel;
import com.mydeal.domain.services.ProductImageService;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class UpdateProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        UpdateProductModel updateProductModel = gson.fromJson(req.getReader(), UpdateProductModel.class);
        System.out.println(updateProductModel);
        ProductService productService = new ProductService();
        ProductImageService productImageService = new ProductImageService();

        boolean dataUpdated = productService.updateProduct(updateProductModel);
        if (updateProductModel.getDeleted().length > 0) {
            System.out.println("Deleted Images");
            productImageService.deletedImagesToProduct(updateProductModel);
        }
        if (updateProductModel.getAdded().length > 0) {
            System.out.println("Added Images");
            productImageService.addNewImagesToProduct(updateProductModel);
        }

        System.out.println(dataUpdated);

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
