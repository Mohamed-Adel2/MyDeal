package com.mydeal.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetail {
    private int id;
    private String userName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    private String street;

    private String city;
    private int apartment;
    private List<OrderSummary> orders;

}
