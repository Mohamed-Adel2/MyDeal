package com.mydeal.domain.models.admin;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class AddProductModel {

    private String ProductName;

    private String Description;

    private BigDecimal Price;

    private Integer AvailableQuantity;


    private BigDecimal Rating;

    byte[][] Images;

    int Category;


    public AddProductModel(){

    }

    public AddProductModel(String productName, String description, BigDecimal price, Integer availableQuantity, BigDecimal averageRating, int category, byte[][]Images) {
        this.ProductName = productName;
        this.Description = description;
        this.Price = price;
        this.AvailableQuantity = availableQuantity;
        this.Rating = averageRating;
        this.Images = Images;
        Category = category;
    }
}
