package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.OrderDetails;
import com.mydeal.domain.models.OrderItemModel;

public class OrderDetailsMapping {

    public static OrderItemModel convertEntityToModel(OrderDetails orderItem) {
        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setId(orderItem.getId().getProductId());
        orderItemModel.setProductName(orderItem.getProduct().getProductName());
        orderItemModel.setPrice(Math.round(orderItem.getProduct().getPrice() * 100.0) / 100.0);
        orderItemModel.setQuantity(orderItem.getQuantity());
        orderItemModel.setRating(0.0);
        orderItemModel.setCustomerId(orderItem.getOrder().getCustomer().getId());
        orderItemModel.setImage(orderItem.getProduct().getProductimages().iterator().next().getImage());
        return orderItemModel;
    }
}
