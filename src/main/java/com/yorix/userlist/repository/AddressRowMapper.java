package com.yorix.userlist.repository;

import com.yorix.userlist.model.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AddressRowMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setCountryId(rs.getInt(1));
        address.setCityId(rs.getInt(2));
        address.setStreetId(rs.getInt(3));
        return address;
    }
}
