package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Order;
import com.mydeal.domain.entities.OrderDetails;
import com.mydeal.domain.models.OrderModel;

public class OrderMapping {
    public static OrderModel mapOrderToOrderModel(Order order) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(order.getId());
        orderModel.setDate(order.getOrderDate().toString());
        int cnt = 0;
        double totalPrice = 0;
        for (OrderDetails orderDetails : order.getOrderdetails()) {
            cnt += orderDetails.getQuantity();
            totalPrice += orderDetails.getQuantity() * orderDetails.getProduct().getPrice();
        }
        orderModel.setItemsCount(cnt);
        orderModel.setTotalPrice(totalPrice);
        return orderModel;
    }
}
