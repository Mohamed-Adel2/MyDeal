package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.entities.CustomerCart;
import com.mydeal.domain.entities.CustomerCartId;
import com.mydeal.domain.entities.Product;
import com.mydeal.domain.models.CartModel;

public class CartMapping {
    public static CartModel mapToCartModel(CustomerCart customerCart) {
        CartModel cartModel = new CartModel();
        cartModel.setCustomerId(customerCart.getId().getCustomerId());
        cartModel.setProductId(customerCart.getId().getProductId());
        cartModel.setQuantity(customerCart.getQuantity());
        return cartModel;
    }

    public static CustomerCart mapToCustomerCart(CartModel cartModel) {
        CustomerCart customerCart = new CustomerCart();
        customerCart.setId(new CustomerCartId());
        customerCart.getId().setCustomerId(cartModel.getCustomerId());
        customerCart.getId().setProductId(cartModel.getProductId());
        customerCart.setQuantity(cartModel.getQuantity());
        return customerCart;
    }
}
