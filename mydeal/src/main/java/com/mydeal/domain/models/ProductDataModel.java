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


    private BigDecimal price;


    private Integer availableQuantity;


    private BigDecimal averageRating;


   // private Category category;

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
