package com.yorix.userlist.service;

import com.yorix.userlist.model.User;
import com.yorix.userlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public User searh(String firstname, String lastname) {
        return userRepository.search(firstname, lastname);
    }
}
