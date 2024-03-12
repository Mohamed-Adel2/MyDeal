package com.mydeal.repository;

import com.mydeal.domain.entities.Address;

public class AddressRepository extends CrudRepository<Address>{
    public AddressRepository() {
        setEntityClass(Address.class);
    }
}
