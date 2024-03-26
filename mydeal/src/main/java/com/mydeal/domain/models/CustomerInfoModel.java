package com.mydeal.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoModel {
    private String email;
    private String phoneNumber;
    private String dob;
    private String password;
    private String userName;
    private String creditLimit;

    private String street;
    private String city;
    private Integer apartment;
}
