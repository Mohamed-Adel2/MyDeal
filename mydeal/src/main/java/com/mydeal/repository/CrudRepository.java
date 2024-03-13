package com.mydeal.repository;

import jakarta.persistence.EntityManager;

public abstract class CrudRepository<T> {
    private Class<T> entityClass;
    protected final EntityManager em;

    protected CrudRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public void setEntityClass(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    public void create( T entity){
        em.persist(entity);
    }
    public T read( Object id){
        return em.find(entityClass, id);
    }

    public T update( T entity){
        return em.merge(entity);
    }

    public void delete( T entity){
        em.remove(entity);
    }
}
