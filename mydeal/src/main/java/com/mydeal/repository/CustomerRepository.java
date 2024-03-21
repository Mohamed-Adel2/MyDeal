package com.mydeal.repository;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.JpaUtil;
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
    public Customer getCustomerByEmailAndPassword(EntityManager em, String email, String password) {
        String jpql = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password";
        TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
        query.setParameter(RequestKey.RQ_CustomerEmail, email);
        query.setParameter(RequestKey.RQ_CustomerPassword, password);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public String checkFieldAvailability(EntityManager em, String fieldName, String fieldValue) {
        String jpql = "SELECT c." + fieldName + " FROM Customer c WHERE c." + fieldName + " = :fieldValue";
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("fieldValue", fieldValue);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public boolean checkAdmin(EntityManager em, String email){
        //will handle with database when change in schema validation of user
        return email.equals("mydeal@gmail.com");
    }
}
