package com.yorix.userlist.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yorix.userlist.model.User;
import com.yorix.userlist.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public ResponseEntity create(String request) {
        JsonNode jsonNode;
        String firstname = "";
        String lastname = "";
        String country = "";
        String city = "";
        String street = "";

        try {
            jsonNode = new ObjectMapper().readValue(request, JsonNode.class);
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
            return ResponseEntity.status(406).body(String.format(invalidAddress, country, city, street));
        }

        User user = userDao.getUser(firstname, lastname);
        if (user != null) {
            return ResponseEntity.status(204).body(String.format(alreadyExists, firstname, lastname));
        }
        user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAddressId(addressId);

        userDao.createUser(user);
        return ResponseEntity.status(201).body(String.format(created, firstname, lastname));
    }

    @Override
    public User getUser(String firstname, String lastname) {
        return userDao.getUser(firstname, lastname);
    }
}
