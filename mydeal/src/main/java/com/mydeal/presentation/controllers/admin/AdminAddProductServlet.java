package com.mydeal.presentation.controllers.admin;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.ProductImageModel;
import com.mydeal.domain.models.admin.AddImageModel;
import com.mydeal.domain.models.admin.AddProductModel;
import com.mydeal.domain.services.CustomerLoginService;
import com.mydeal.domain.services.ProductImageService;
import com.mydeal.domain.services.ProductService;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.presentation.utils.ClientJSAlert;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Optional;

public class AdminAddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        AddProductModel addProductModel = gson.fromJson(req.getReader(), AddProductModel.class);
        ProductService productService = new ProductService();
        int id = productService.addProduct(addProductModel);

        // Handle images
        ProductImageModel[] imageModels = handleRequestOfImage(req, id, addProductModel);
        ProductImageService productImageService = new ProductImageService();
        int imagesAdded = productImageService.setImages(imageModels);
        /*AddProductModel addProductModel = handleRequestOfProduct(req);
        ProductService productService = new ProductService();
        int id =productService.addProduct(addProductModel);
        ProductImageModel[] ImageModels = handleRequestOfImage(req, id);
        ProductImageService productImageService = new ProductImageService();
        int ImagedAdded = productImageService.setImages(ImageModels);*/

    }

    public ProductImageModel[] handleRequestOfImage(HttpServletRequest req, int id, AddProductModel addProductModel){
      //  String[] imageStrings = req.getParameterValues("Images");
        int size= addProductModel.getImages().length;
        byte[][] imageBytes = new byte[size+1][];
        ProductImageModel[] images = new ProductImageModel[size+1];
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
