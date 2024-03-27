package com.mydeal.presentation.filters;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.services.CustomerLoginService;
import com.mydeal.domain.util.RequestKey;
import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String email = request.getParameter(RequestKey.RQ_CustomerEmail);
        String password = request.getParameter(RequestKey.RQ_CustomerPassword);HttpServletResponse resp = (HttpServletResponse) response;
        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
            CustomerLoginService customerLoginService = new CustomerLoginService();
            Customer customer = customerLoginService.login(email, password);
            if (customer != null) {
                CustomerDataModel customerDataModel = CustomerMapping.convertEntityToModel(customer);
                if (customerDataModel.getIsAdmin() == 1) {
                    // if the user is an Admin , then navigate to /admin/home servlet .
                    resp.sendRedirect("/admin/login");
                }
                else if (customerDataModel.getIsAdmin() == 0) chain.doFilter(request, response);
            }
        } else {
            // if there is something wrong in credentials , then refresh login page .
            resp.sendRedirect("/login");
        }

    }
}
