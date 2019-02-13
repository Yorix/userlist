package com.yorix.userlist.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yorix.userlist.model.User;
import com.yorix.userlist.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    @Value("${created}")
    private String created;
    @Value("${invalid-address}")
    private String invalidAddress;
    @Value("${already-exists}")
    private String alreadyExists;

    private final UserDao userDao;
    private HttpHeaders headers;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public ResponseEntity create(String request) {
        headers = new HttpHeaders();
        String firstname = "";
        String lastname = "";
        String country = "";
        String city = "";
        String street = "";

        try {
            JsonNode jsonNode = new ObjectMapper().readValue(request, JsonNode.class);
            firstname = jsonNode.get("firstname").textValue();
            lastname = jsonNode.get("lastname").textValue();
            country = jsonNode.get("country").textValue();
            city = jsonNode.get("city").textValue();
            street = jsonNode.get("street").textValue();
        } catch (IOException e) {
            e.printStackTrace(); //todo
        }

        int countryId = userDao.getAddressElementId("country", country);
        int cityId = userDao.getAddressElementId("city", city);
        int streetId = userDao.getAddressElementId("street", street);

        int addressId = userDao.getAddressId(countryId, cityId, streetId);
        if (addressId == -1) {
            headers.add("status", "406");
            headers.add("message", String.format(invalidAddress, country, city, street));
            return new ResponseEntity(headers, HttpStatus.NOT_ACCEPTABLE);
        }

        User user = userDao.getUser(firstname, lastname);
        if (user != null) {
            headers.add("status", "204");
            headers.add("message", String.format(alreadyExists, firstname, lastname));
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        }

        user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAddressId(addressId);

        userDao.createUser(user);

        headers.add("status", "201");
        headers.add("message", String.format(created, firstname, lastname));
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @Override
    public User getUser(String firstname, String lastname) {
        return userDao.getUser(firstname, lastname);
    }
}
