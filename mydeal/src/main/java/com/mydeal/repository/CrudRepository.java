package com.mydeal.repository;

import jakarta.persistence.EntityManager;

public abstract class CrudRepository<T> {
    private Class<T> entityClass;

    public void setEntityClass(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    public void create(EntityManager em, T entity){
        em.persist(entity);
    }
    public T read(EntityManager em, Object id){
        return em.find(entityClass, id);
    }

    public T update(EntityManager em, T entity){
        return em.merge(entity);
    }

    public void delete(EntityManager em, T entity){
        em.remove(entity);
    }
}
