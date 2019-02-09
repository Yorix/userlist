package com.yorix.userlist.service;

import com.yorix.userlist.model.Address;
import com.yorix.userlist.model.User;
import com.yorix.userlist.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //todo !!!!!!
    @Override
    public ResponseEntity create(String request) {
        try {
            JSONObject object = new JSONObject(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String responseBody =
                String.format("{\"status\": 204, \"message\": \"User %s %s already exists.\"}", userFirstname, userFirstname);
        return ResponseEntity.status(204).body(responseBody);

        userRepository.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Override
    public User searh(String firstname, String lastname) {
        return userRepository.search(firstname, lastname);
    }


    private Address getAddress(String request) {
        Address address = new Address();
        try {
            JSONObject object = new JSONObject(request);
            address.setCountryId(object.getInt("country_id"));
            address.setCityId(object.getInt("city_id"));
            address.setStreetId(object.getInt("street_id"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return address;
    }


    private User getUser(String request) {
        User user = new User();
        return user;
    }
}
