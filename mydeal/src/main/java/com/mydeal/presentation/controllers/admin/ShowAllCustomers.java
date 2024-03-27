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
        String email = req.getParameter("searchKey");
        int startIdx = Integer.parseInt(req.getParameter("startIdx"));
        int limit = Integer.parseInt(req.getParameter("limit"));

        CustomerDataService customerDataService = new CustomerDataService();
        List<CustomerDataModel> customersDataModels = customerDataService.getAllCustomers(email, startIdx, limit);
        Gson gson = new Gson();
        String json = gson.toJson(customersDataModels);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
