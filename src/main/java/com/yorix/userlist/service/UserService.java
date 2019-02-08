package com.yorix.userlist.service;

import com.yorix.userlist.model.User;

public interface UserService {
    void create(User user);

    User searh(String firstname, String lastname);
}
