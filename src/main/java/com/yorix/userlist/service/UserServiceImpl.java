package com.yorix.userlist.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yorix.userlist.model.Address;
import com.yorix.userlist.model.User;
import com.yorix.userlist.repository.AddressDao;
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
    @Value("${user-created}")
    private String created;
    @Value("${invalid-address}")
    private String invalidAddress;
    @Value("${user-already-exists}")
    private String alreadyExists;
    @Value("${user-doesnt-exist}")
    private String userDoesntExist;


    private final UserDao userDao;
    private final AddressDao addressDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, AddressDao addressDao) {
        this.userDao = userDao;
        this.addressDao = addressDao;
    }


    @Override
    public ResponseEntity<String> create(String request) {
        HttpHeaders headers = new HttpHeaders();
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
            e.printStackTrace();
        }

        int countryId = addressDao.getAddressElementId("country", country);
        int cityId = addressDao.getAddressElementId("city", city);
        int streetId = addressDao.getAddressElementId("street", street);

        Address address = addressDao.getAddress(countryId, cityId, streetId);
        if (address == null) {
            headers.add("status", "406");
            headers.add("message", String.format(invalidAddress, country, city, street));
            String body = String.format(
                    "{\"status\": 406, \"message\": " + invalidAddress + "}", country, city, street);
            return new ResponseEntity<>(body, headers, HttpStatus.NOT_ACCEPTABLE);
        }
        int addressId = address.getId();

        User user = userDao.getUser(firstname, lastname);
        if (user != null) {
            headers.add("status", "204");
            headers.add("message", String.format(alreadyExists, firstname, lastname));
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAddressId(addressId);

        userDao.createUser(user);

        headers.add("status", "201");
        headers.add("message", String.format(created, firstname, lastname));
        String body = String.format(
                "{\"status\": 201, \"message\": " + created + "}", firstname, lastname);
        return new ResponseEntity<>(body, headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> getUser(String firstname, String lastname) {
        HttpHeaders headers = new HttpHeaders();

        User user = userDao.getUser(firstname, lastname);

        if (user == null) {
            headers.add("status", "204");
            headers.add("message", String.format(userDoesntExist, firstname, lastname));
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        firstname = user.getFirstname();
        lastname = user.getLastname();
        Address address = addressDao.getAddressById(user.getAddressId());
        String country = addressDao.getElementValue("country", address.getCountryId());
        String city = addressDao.getElementValue("city", address.getCityId());
        String street = addressDao.getElementValue("street", address.getStreetId());


        headers.add("status", "200");
        headers.add("firstname", firstname);
        headers.add("lastname", lastname);
        headers.add("country", country);
        headers.add("city", city);
        headers.add("street", street);
        String body = String.format(
                "{\"status\": 200, \"firstname\":\"%s\", \"lastname\": \"%s\", " +
                        "\"country\": \"%s\", \"city\": \"%s\", \"street\": \"%s\"}",
                firstname, lastname, country, city, street
        );
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
