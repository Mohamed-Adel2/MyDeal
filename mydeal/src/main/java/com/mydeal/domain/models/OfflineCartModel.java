package com.mydeal.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OfflineCartModel {
    ArrayList<CartModel> cartItems = new ArrayList<>();

    @Override
    public String toString() {
        return "OfflineCartModel{" +
                "cartItems=" + cartItems +
                '}';
    }
}
