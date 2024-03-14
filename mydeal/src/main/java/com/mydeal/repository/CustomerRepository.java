package com.mydeal.repository;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.RequestKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CustomerRepository extends CrudRepository<Customer> {
    public CustomerRepository() {
        setEntityClass(Customer.class);
    }

    /**
     * Get Customer by email and password , used in login process .
     *
     * @param email
     * @param password
     * @return
     */
    public CustomerDataModel getCustomerByEmailAndPassword(String email, String password) {
        String jpql = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password";
        TypedQuery<Customer> query = CustomerDataModel.getInstance().getEm().createQuery(jpql, Customer.class);
        query.setParameter(RequestKey.RQ_CustomerEmail, email);
        query.setParameter(RequestKey.RQ_CustomerPassword, password);
        try {
            return new CustomerDataModel(query.getSingleResult());
        } catch (NoResultException e) {
            return null; // Handle the case where no customer is found with the provided email and password
        }
    }
}
