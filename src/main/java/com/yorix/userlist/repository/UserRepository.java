package com.yorix.userlist.repository;

import com.yorix.userlist.model.User;

public interface UserRepository {
    void create(User user);

    User search(String... args);
}
