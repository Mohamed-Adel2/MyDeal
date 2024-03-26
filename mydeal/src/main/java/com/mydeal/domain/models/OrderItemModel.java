package com.mydeal.domain.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModel {
    private Integer id;
    private Integer customerId;
    private String productName;
    private Double rating;
    private Double price;
    private Integer quantity;
    private byte[] image;

}
