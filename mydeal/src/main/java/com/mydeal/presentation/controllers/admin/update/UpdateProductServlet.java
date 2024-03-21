package com.mydeal.presentation.controllers.admin.update;

import com.mydeal.domain.models.ProductDataModel;
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
        ProductDataModel productDataModel = handleRequest(req);
        if (productDataModel.getId() == 0) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Failed To Update");
        } else {
            ProductService productService = new ProductService();
            boolean isUpdated = productService.updateProduct(productDataModel);
            if (isUpdated) {
                PrintWriter printWriter = resp.getWriter();
                printWriter.write("Updated Successfully");
            } else {
                PrintWriter printWriter = resp.getWriter();
                printWriter.write("Failed To Update");
            }
        }

    }

    public ProductDataModel handleRequest(HttpServletRequest req) {
        ProductDataModel productDataModel = new ProductDataModel();
        productDataModel.setId(Integer.valueOf(req.getParameter("ProductId")));
        productDataModel.setProductName(req.getParameter("ProductName"));
        productDataModel.setAvailableQuantity(Integer.valueOf(req.getParameter("AvalibleQuantity")));
        productDataModel.setDescription(req.getParameter("Description"));
        productDataModel.setCategory(Integer.parseInt(req.getParameter("Category")));
        Double price = Double.parseDouble(req.getParameter("Price"));
        productDataModel.setPrice(price);
        return productDataModel;
    }
}
