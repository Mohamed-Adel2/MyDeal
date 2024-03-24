package com.mydeal.presentation.controllers.admin;

import com.google.gson.Gson;
import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.services.CustomerDataService;
import com.mydeal.domain.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowAllCustomers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager customerEM = JpaUtil.createEntityManager();
        CustomerDataService customerDataService = new CustomerDataService();
        customerEM.getTransaction().begin();
        /**
         * Load all Customers from Database .
         */
        TypedQuery<Customer> query = customerEM.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        List<CustomerDataModel> customersDataModels = new ArrayList<>();
        customers.forEach(customer -> customersDataModels.add(CustomerMapping.convertEntityToModel(customer)));
        customerEM.getTransaction().commit();
        customerEM.close();

        Gson gson = new Gson();

        String jsonCustomers = gson.toJson(customersDataModels);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonCustomers);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
