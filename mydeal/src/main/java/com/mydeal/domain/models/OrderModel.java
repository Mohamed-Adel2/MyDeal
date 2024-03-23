package com.mydeal.domain.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private Integer id;
    private String date;
    private Integer itemsCount;
    private Double totalPrice;
}
