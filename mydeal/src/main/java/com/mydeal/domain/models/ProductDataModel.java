package com.mydeal.domain.models;

import com.mydeal.domain.entities.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDataModel implements Serializable {
    private Integer id;

    private String productName;


    private String description;


    private Double price;


    private Integer availableQuantity;


    private Double averageRating;

    private int category;

    private int isRemoved;

    private byte[] image;

    @Override
    public String toString() {
        return "ProductDataModel{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", availableQuantity=" + availableQuantity +
                '}';
    }
}
