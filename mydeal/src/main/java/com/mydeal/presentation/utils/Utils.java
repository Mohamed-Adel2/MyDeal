package com.mydeal.presentation.utils;

import com.mydeal.domain.entities.Address;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.util.RequestKey;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Utils {
    public static void setCustomerAttributes(Customer customer, HttpServletRequest request) {
        customer.setUsername(request.getParameter(RequestKey.RQ_CustomerUserName));
        customer.setEmail(request.getParameter(RequestKey.RQ_CustomerEmail));
        customer.setPhoneNumber(request.getParameter(RequestKey.RQ_CustomerPhoneNumber));
        customer.setDateOfBirth(LocalDate.parse(request.getParameter(RequestKey.RQ_CustomerDOB)));
        customer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(request.getParameter(RequestKey.RQ_CustomerCreditLimit))));
        Address address = (customer.getAddress() == null ? new Address() : customer.getAddress());
        address.setCity(request.getParameter(RequestKey.RQ_CustomerCity));
        address.setStreet(request.getParameter(RequestKey.RQ_CustomerStreet));
        address.setApartment(Integer.parseInt(request.getParameter(RequestKey.RQ_CustomerApartment)));
        customer.setAddress(address);
        customer.setPassword(request.getParameter(RequestKey.RQ_CustomerPassword));
    }
}
