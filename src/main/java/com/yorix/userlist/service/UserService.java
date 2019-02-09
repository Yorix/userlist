package com.yorix.userlist.service;

import com.yorix.userlist.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity create(String request);

    User searh(String firstname, String lastname);
}
