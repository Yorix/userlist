package com.yorix.userlist.repository;

import com.yorix.userlist.model.Address;

public interface AddressDao {
    Address getAddress(Integer... args);

    Address getAddressById(int addressId);

    int getAddressElementId(String table, String elementName);

    String getElementValue(String table, int elementId);
}
