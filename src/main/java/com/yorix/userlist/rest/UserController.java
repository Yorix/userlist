package com.yorix.userlist.rest;

import com.yorix.userlist.model.User;
import com.yorix.userlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "get/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> get(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname) {
        User user = userService.searh(firstname, lastname);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody String request) {
        return userService.create(request);
    }
}
