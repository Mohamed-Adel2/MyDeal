package com.mydeal.repository;

import com.mydeal.domain.entities.Customer;
import com.mydeal.domain.mapping.CustomerMapping;
import com.mydeal.domain.models.CustomerDataModel;
import com.mydeal.domain.util.JpaUtil;
import com.mydeal.domain.util.RequestKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CustomerRepository extends CrudRepository<Customer> {
    public CustomerRepository() {
        setEntityClass(Customer.class);
    }

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

    public boolean checkAdmin(EntityManager em, String email) {
        TypedQuery<Customer> query = em.createQuery("SELECT u FROM Customer u WHERE u.email = :email", Customer.class);
        query.setParameter("email", email);
           Customer customer = query.getSingleResult();
           if(customer.getIsAdmin()==0){
               return false;
           }else{
               return true;
           }
    }

    public double getCustomerBalance(EntityManager em, int customerId) {
        TypedQuery<Double> query = em.createQuery("SELECT c.creditLimit FROM Customer c WHERE c.id = :customerId", Double.class);
        query.setParameter("customerId", customerId);
        return query.getSingleResult();
    }

    public List<Customer> getAllCustomer(EntityManager em, String email, int startIdx, int limit){
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.email LIKE :email AND c.isAdmin=0", Customer.class);
        query.setParameter("email", "%" + email + "%");
        query.setFirstResult(startIdx);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
