package com.mydeal.domain.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDetailDataModel {
   private int id;

   private String productName;

   private byte [][]images;

   private Double price;

    private String description;

    private Integer availableQuantity;

    private Double averageRating;

    private Integer quantity;

    private String category;

    private Integer isRemoved;

    private int[] IdsOfImages;
}
