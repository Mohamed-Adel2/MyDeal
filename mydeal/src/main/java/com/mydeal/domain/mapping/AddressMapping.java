package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Address;
import com.mydeal.domain.models.AddressDataModel;

public class AddressMapping {
    public static AddressDataModel convertEntityToModel(Address address) {
        AddressDataModel addressDataModel = new AddressDataModel();
        addressDataModel.setId(address.getId());
        addressDataModel.setStreet(address.getStreet());
        addressDataModel.setCity(address.getCity());
        addressDataModel.setApartment(address.getApartment());
        return addressDataModel;
    }

    public static Address convertModelToEntity(AddressDataModel addressDataModel) {
        Address address = new Address();
        address.setId(addressDataModel.getId());
        address.setStreet(addressDataModel.getStreet());
        address.setCity(addressDataModel.getCity());
        address.setApartment(addressDataModel.getApartment());
        return address;
    }
}
