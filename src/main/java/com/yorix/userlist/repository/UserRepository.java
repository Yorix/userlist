package com.yorix.userlist.repository;

import com.yorix.userlist.model.Address;
import com.yorix.userlist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepository implements UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO `user` (`firstname`, `lastname`, `address_id`) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getFirstname(), user.getLastname(), user.getAddressId()); //todo
    }

    @Override
    public User getUser(String... args) {
        String sql = "SELECT * FROM `user` WHERE `firstname` = ? AND `lastname` = ?";
        List<User> users = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(User.class));
        if (users.size() == 1) {
            return users.get(0);
        } else return null;
    }

    //TODO
    @Override
    public int getAddressId(Integer... args) {
        String sql = "SELECT * FROM `address` WHERE `country_id` = ? AND `city_id` = ? AND `street_id` = ?";
        List<Address> addresses = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(Address.class));
        if (addresses.size() == 1) {
            return addresses.get(0).getId();
        } else return -1;
    }

    @Override
    public int getAddressElementId(String table, String elementName) {
        String sql = String.format("SELECT `id` FROM `%s` WHERE `name` = ?", table);
        List<Integer> id = jdbcTemplate.queryForList(sql, Integer.TYPE, elementName);
        if (id.size() == 1) {
            return id.get(0);
        } else return -1;
    }
}
