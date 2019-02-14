package com.yorix.userlist.repository;

import com.yorix.userlist.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AddressRepository implements AddressDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Address getAddress(Integer... args) {
        String sql = "SELECT * FROM `userlist`.`address` WHERE `country_id` = ? AND `city_id` = ? AND `street_id` = ?";
        List<Address> addresses = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(Address.class));
        if (addresses.size() == 1) {
            return addresses.get(0);
        } else return null;
    }

    @Override
    public Address getAddressById(int addressId) {
        String sql = "SELECT * FROM `userlist`.`address` WHERE `id` = " + addressId;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Address.class));
    }

    @Override
    public int getAddressElementId(String table, String elementName) {
        String sql = String.format("SELECT `id` FROM `%s` WHERE `name` = ?", table);
        List<Integer> id = jdbcTemplate.queryForList(sql, Integer.TYPE, elementName);
        if (id.size() == 1) {
            return id.get(0);
        } else return -1;
    }

    @Override
    public String getElementValue(String table, int elementId) {
        String sql = String.format("SELECT `name` FROM `%s` WHERE `id` = ?", table);
        List<String> names = jdbcTemplate.queryForList(sql, String.class, elementId);
        if (names.size() == 1) {
            return names.get(0);
        } else return "";
    }
}
