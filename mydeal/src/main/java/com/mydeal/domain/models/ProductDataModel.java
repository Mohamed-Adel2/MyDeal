package com.mydeal.domain.models;

import com.mydeal.domain.entities.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDataModel {
    private Integer id;


    private String productName;


    private String description;


    private BigDecimal price;


    private Integer availableQuantity;


    private BigDecimal averageRating;


    private Category category;

    private Byte[] image;

}
