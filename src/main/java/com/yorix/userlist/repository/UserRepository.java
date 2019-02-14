package com.yorix.userlist.repository;

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
        String sql = "INSERT INTO `userlist`.`user` (`firstname`, `lastname`, `address_id`) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getFirstname(), user.getLastname(), user.getAddressId());
    }

    @Override
    public User getUser(String... args) {
        String sql = "SELECT * FROM `userlist`.`user` WHERE `firstname` = ? AND `lastname` = ?";
        List<User> users = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(User.class));
        if (users.size() == 1) {
            return users.get(0);
        } else return null;
    }
}
