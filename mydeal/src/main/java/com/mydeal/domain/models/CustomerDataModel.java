package com.mydeal.domain.models;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.util.JpaUtil;
import jakarta.persistence.EntityManager;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerDataModel {
    private EntityManager em = JpaUtil.createEntityManager();
    // make this class singleton using getinstance method
    private Integer id;
    private String userName, email, phoneNumber, dob, address, password;
    private BigDecimal creditLimit;
    /**
     * make this class Singleton .
     */
    private static CustomerDataModel instance;

    private CustomerDataModel() {
        // private constructor to prevent instantiation
    }

    public static CustomerDataModel getInstance() {
        if (instance == null) {
            instance = new CustomerDataModel();
        }
        return instance;
    }

    public Customer convertToCustomerEntity() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setUsername(this.userName);
        customer.setEmail(this.email);
        customer.setPhoneNumber(this.phoneNumber);
        LocalDate dob = LocalDate.parse(this.dob);
        customer.setDateOfBirth(dob);
        customer.setPassword(this.password);
        customer.setCreditLimit(this.creditLimit);
        return customer;
    }

    public CustomerDataModel(Customer customerEntity) {
        this.id = customerEntity.getId();
        this.userName = customerEntity.getUsername();
        this.email = customerEntity.getEmail();
        this.phoneNumber = customerEntity.getPhoneNumber();
        this.dob = customerEntity.getDateOfBirth().toString();
        this.address = customerEntity.getAddress().toString() == null ? "" : customerEntity.getAddress().toString();
        this.password = customerEntity.getPassword();
        this.creditLimit = customerEntity.getCreditLimit();
    }

    public EntityManager getEm() {
        return em;
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

    public String getAddress() {
        return address;
    }

    public CustomerDataModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public CustomerDataModel setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    @Override
    public String toString() {
        return "CustomerDataModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", creditLimit=" + creditLimit +
                '}';
    }
}
