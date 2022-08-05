package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        return user;
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);

    }
}
