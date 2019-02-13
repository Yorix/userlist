package com.yorix.userlist.service;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> create(String request);

    ResponseEntity<String> getUser(String firstname, String lastname);
}
