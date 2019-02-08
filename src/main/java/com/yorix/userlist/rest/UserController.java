package com.yorix.userlist.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yorix.userlist.model.User;
import com.yorix.userlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity add(@RequestBody @Valid User user) {
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

//    @PutMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<User> update(@RequestBody @Valid User user) {
//        HttpHeaders headers = new HttpHeaders();
//
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        userService.update(user);
//
//        return new ResponseEntity<>(user, headers, HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<User> delete(@PathVariable("id") Integer id) {
//        if (id == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        if (userService.getById(id) == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        userService.delete(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<List<User>> getAll() {
//        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
//    }
}
