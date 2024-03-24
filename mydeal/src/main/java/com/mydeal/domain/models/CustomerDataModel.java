package com.mydeal.domain.models;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.util.JpaUtil;
import jakarta.persistence.EntityManager;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerDataModel {

    private Integer id, addressId, isAdmin;
    private String userName, email, phoneNumber, dob, password;
    private Double creditLimit;
    private AddressDataModel addressDataModel;

    public CustomerDataModel() {

    }

    public Integer getAddressId() {
        return addressId;
    }

    public CustomerDataModel setAddressId(Integer addressId) {
        this.addressId = addressId;
        return this;
    }

    public AddressDataModel getAddressDataModel() {
        return addressDataModel;
    }

    public CustomerDataModel setAddressDataModel(AddressDataModel addressDataModel) {
        this.addressDataModel = addressDataModel;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CustomerDataModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public CustomerDataModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CustomerDataModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerDataModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerDataModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public CustomerDataModel setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public CustomerDataModel setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    @Override
    public String toString() {
        return "CustomerDataModel{" +
                "id=" + id +
                ", addressId=" + addressId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob='" + dob + '\'' +
                ", password='" + password + '\'' +
                ", creditLimit=" + creditLimit +
                ", addressDataModel=" + addressDataModel +
                '}';
    }
}
