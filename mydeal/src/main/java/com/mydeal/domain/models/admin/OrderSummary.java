package com.mydeal.domain.models.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {
    private int orderId;
    private int customerId;
    private LocalDate orderDate;

}
