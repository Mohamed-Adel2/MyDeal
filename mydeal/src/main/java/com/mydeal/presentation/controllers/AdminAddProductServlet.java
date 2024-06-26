package com.mydeal.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mydeal.domain.models.ProductImageModel;
import com.mydeal.domain.models.AddProductModel;
import com.mydeal.domain.services.ProductImageService;
import com.mydeal.domain.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AdminAddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        AddProductModel addProductModel = gson.fromJson(req.getReader(), AddProductModel.class);
        ProductService productService = new ProductService();
        int id = productService.addProduct(addProductModel);
        JsonObject jsonResponse = new JsonObject();
        if (id == -1) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Failed to add product or images.");
        } else {
            // Handle images
            ProductImageModel[] imageModels = handleRequestOfImage(req, id, addProductModel);
            ProductImageService productImageService = new ProductImageService();
            int imagesAdded = productImageService.setImages(imageModels);
            if (id > 0 && imagesAdded > 0) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Product and images added successfully.");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Failed to add product or images.");
            }
        }

        // Set response content type
        resp.setContentType("application/json");

        // Write response JSON to output stream
        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse.toString());
        }

    }

    public ProductImageModel[] handleRequestOfImage(HttpServletRequest req, int id, AddProductModel addProductModel) {
        //  String[] imageStrings = req.getParameterValues("Images");
        int size = addProductModel.getImages().length;
        byte[][] imageBytes = new byte[size + 1][];
        ProductImageModel[] images = new ProductImageModel[size + 1];
        for (int i = 0; i < size; i++) {
            //imageBytes[i] = Base64.getDecoder().decode(imageStrings[i]);
            imageBytes[i] = addProductModel.getImages()[i];
            ProductImageModel ImageModel = new ProductImageModel();
            ImageModel.setProductId(id);
            ImageModel.setImage(imageBytes[i]);
            images[i] = ImageModel;
        }
        return images;
    }
}
