package com.mydeal.domain.models;

import lombok.Data;

import java.util.Arrays;

@Data
public class UpdateProductModel {
    int ProductId;

    String ProductName;

    String Description;

    double price;

    int quantity;

    String category;

    int[] deleted;

    byte[][] added;

    @Override
    public String toString() {
        return "UpdateProductModel{" +
                "ProductId=" + ProductId +
                ", ProductName='" + ProductName + '\'' +
                ", Description='" + Description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", deleted=" + deleted.length +
                ", added=" + added.length +
                '}';
    }
}
