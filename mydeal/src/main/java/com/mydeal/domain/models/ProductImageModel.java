package com.mydeal.domain.models;

import lombok.Data;

@Data
public class ProductImageModel {
    int productId;

    byte[] image;
}
