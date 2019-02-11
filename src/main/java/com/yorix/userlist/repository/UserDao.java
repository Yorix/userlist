package com.yorix.userlist.repository;

import com.yorix.userlist.model.User;

public interface UserDao {
    void createUser(User user);

    User getUser(String... args);

    int getAddressId(Integer... args);

    int getAddressElementId(String table, String elementName);
}
