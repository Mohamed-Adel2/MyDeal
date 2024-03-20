package com.mydeal.presentation.utils;

import com.mydeal.domain.entities.Address;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.util.RequestKey;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Utils {
    public static void setCustomerAttributes(Customer customer, HttpServletRequest request) {
        System.out.println("Setting customer attributes");
        customer.setUsername(request.getParameter(RequestKey.RQ_CustomerUserName));
        System.out.println("Setting customer attributes1");
        customer.setEmail(request.getParameter(RequestKey.RQ_CustomerEmail));
        System.out.println("Setting customer attributes2");
        customer.setPhoneNumber(request.getParameter(RequestKey.RQ_CustomerPhoneNumber));
        System.out.println(request.getParameter(RequestKey.RQ_CustomerDOB));
        customer.setDateOfBirth(LocalDate.parse(request.getParameter(RequestKey.RQ_CustomerDOB)));
        System.out.println("Setting customer attributes4");
        customer.setCreditLimit(Double.parseDouble(request.getParameter(RequestKey.RQ_CustomerCreditLimit)));
        System.out.println("Setting customer attributes5");
        Address address = customer.getAddress();
        System.out.println("Setting customer attributes6");
        address.setCity(request.getParameter(RequestKey.RQ_CustomerCity));
        System.out.println("Setting customer attributes7");
        address.setStreet(request.getParameter(RequestKey.RQ_CustomerStreet));
        System.out.println("Setting customer attributes8");
        address.setApartment(Integer.parseInt(request.getParameter(RequestKey.RQ_CustomerApartment)));
        System.out.println("Setting customer attributes9");
        customer.setPassword(request.getParameter(RequestKey.RQ_CustomerPassword));
        System.out.println("Setting customer attributes10");
    }
}
