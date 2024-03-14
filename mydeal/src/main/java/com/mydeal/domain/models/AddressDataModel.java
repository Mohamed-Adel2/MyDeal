package com.mydeal.domain.models;

public class AddressDataModel {
    private Integer id, apartment, customerId;
    private String street, city;

    public AddressDataModel() {

    }

    public Integer getId() {
        return id;
    }

    public AddressDataModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getApartment() {
        return apartment;
    }

    public AddressDataModel setApartment(Integer apartment) {
        this.apartment = apartment;
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public AddressDataModel setCustomerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressDataModel setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressDataModel setCity(String city) {
        this.city = city;
        return this;
    }
}
