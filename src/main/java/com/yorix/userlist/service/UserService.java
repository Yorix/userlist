package com.yorix.userlist.service;

import com.yorix.userlist.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity create(String request);

    User getUser(String firstname, String lastname);
}
