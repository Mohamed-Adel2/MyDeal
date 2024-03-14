package com.mydeal.repository;

import com.mydeal.domain.entities.Address;
import jakarta.persistence.EntityManager;

public class AddressRepository extends CrudRepository<Address>{
    public AddressRepository() {

        setEntityClass(Address.class);
    }
}
