package com.mydeal.domain.models;

import lombok.Data;

@Data
public class UserDataModel {
    private Long id;
    private String userName, email, phoneNumber, dob, address, password;
    private double creditLimit;
    private Boolean isAdmin;

    public String getPassword() {
        return password;
    }

    public UserDataModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserDataModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserDataModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDataModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserDataModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public UserDataModel setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserDataModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public UserDataModel setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public UserDataModel setAdmin(Boolean admin) {
        isAdmin = admin;
        return this;
    }

    @Override
    public String toString() {
        return "UserDataModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", creditLimit=" + creditLimit +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
