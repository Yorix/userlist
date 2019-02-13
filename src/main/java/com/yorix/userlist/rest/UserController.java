package com.yorix.userlist.rest;

import com.yorix.userlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> add(@RequestBody String request) {
        return userService.create(request);
    }

    @GetMapping(value = "get/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> get(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname) {
        return userService.getUser(firstname, lastname);
    }
}
