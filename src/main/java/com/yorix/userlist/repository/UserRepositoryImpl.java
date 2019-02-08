package com.yorix.userlist.repository;

import com.yorix.userlist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(User user) {
        String sql = "INSERT userlist.user (firstname, lastname, address_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getFirstname(), user.getLastname(), user.getAddressId());
    }

    @Override
    public User search(String... args) {
        String sql = "SELECT * FROM userlist.user WHERE firstname = ? AND lastname = ?";
        return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<>(User.class));
    }
}
