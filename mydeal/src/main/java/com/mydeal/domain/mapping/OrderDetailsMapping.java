package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.OrderDetails;
import com.mydeal.domain.models.OrderItemModel;

public class OrderDetailsMapping {

    public static OrderItemModel convertEntityToModel(OrderDetails orderItem) {
        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setId(orderItem.getId().getProductId());
        orderItemModel.setProductName(orderItem.getProduct().getProductName());
        orderItemModel.setPrice(orderItem.getProduct().getPrice());
        orderItemModel.setQuantity(orderItem.getQuantity());
        orderItemModel.setRating(orderItem.getProduct().getAverageRating());
        orderItemModel.setImage(orderItem.getProduct().getProductimages().iterator().next().getImage());
        return orderItemModel;
    }
}
