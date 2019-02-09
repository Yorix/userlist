package com.yorix.userlist.service;

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

    @Override
    public ResponseEntity create(String request) {
        String userFirstname = null;
        String userLastname = null;
        int addressId;

        String countryName;
        String cityName;
        String streetName;

        try {
            JSONObject object = new JSONObject(request);
            userFirstname = object.getString("firstname");
            userLastname = object.getString("lastname");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        User user = searh(userFirstname, userLastname);
        if (user != null) {
            String responseBody =
                    String.format("{\"status\": 204, \"message\": \"User %s %s already exists.\"}", userFirstname, userFirstname);
            return ResponseEntity.status(204).body(responseBody);
        }

        //todo
        user.setFirstname(userFirstname);
        user.setLastname(userLastname);

        userRepository.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Override
    public User searh(String firstname, String lastname) {
        return userRepository.search(firstname, lastname);
    }
}
