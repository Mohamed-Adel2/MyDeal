package com.mydeal.domain.mapping;

import com.mydeal.domain.entities.Address;
import com.mydeal.domain.models.AddressOrderModel;

public class AddressOrderMapping {
    public static AddressOrderModel EntityToModel(Address address) {
        AddressOrderModel addressOrderModel = new AddressOrderModel();
        addressOrderModel.setStreet(address.getStreet());
        addressOrderModel.setCity(address.getCity());
        addressOrderModel.setApartment(address.getApartment());
        return addressOrderModel;
    }
}
