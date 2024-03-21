package com.mydeal.domain.models.admin;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class AddProductModel {

    private String ProductName;

    private String Description;

    private Double Price;

    private Integer AvailableQuantity;


    private Double Rating;

    byte[][] Images;

    int Category;


    public AddProductModel(){

    }

    public AddProductModel(String productName, String description, Double price, Integer availableQuantity, Double averageRating, int category, byte[][]Images) {
        this.ProductName = productName;
        this.Description = description;
        this.Price = price;
        this.AvailableQuantity = availableQuantity;
        this.Rating = averageRating;
        this.Images = Images;
        Category = category;
    }
}
