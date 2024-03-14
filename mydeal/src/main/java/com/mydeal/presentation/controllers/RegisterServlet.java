package com.mydeal.presentation.controllers;

import com.mydeal.domain.entities.Address;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.AddressMapping;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.AddressDataModel;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.domain.util.RequestKey;
import com.mydeal.repository.AddressRepository;
import com.mydeal.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = JpaUtil.createEntityManager();
        CustomerDataModel customerDataModel = new CustomerDataModel();
        customerDataModel
                .setUserName(req.getParameter(RequestKey.RQ_CustomerUserName))
                .setEmail(req.getParameter(RequestKey.RQ_CustomerEmail))
                .setPassword(req.getParameter(RequestKey.RQ_CustomerPassword))
                .setPhoneNumber(req.getParameter(RequestKey.RQ_CustomerPhoneNumber))
                .setDob(req.getParameter(RequestKey.RQ_CustomerDOB))
                .setAddressId(0)
                .setAddressDataModel(new AddressDataModel())
                .setId(null)
                .setCreditLimit(BigDecimal.valueOf(0.0));

        // Convert AddressDataModel to Address entity
        Address addressEntity = AddressMapping.convertModelToEntity(customerDataModel.getAddressDataModel());

        // Persist the Address entity
        AddressRepository addressRepository = new AddressRepository();
        EntityManager addressEm = JpaUtil.createEntityManager();
        addressRepository.create(addressEm, addressEntity);

        // Associate the persisted Address entity with the CustomerDataModel
        customerDataModel.setAddressId(addressEntity.getId());

        // Convert CustomerDataModel to Customer entity
        Customer customerEntity = CustomerMapping.convertModelToEntity(customerDataModel);

        // Set the Address entity to the Customer entity
        customerEntity.setAddress(addressEntity);

        // Persist the Customer entity
        CustomerRepository customerRepository = new CustomerRepository();
        customerRepository.setEntityClass(Customer.class);
        customerRepository.create(em, customerEntity);

        // Commit and close EntityManagers
        em.close();
        addressEm.close();
    }
}