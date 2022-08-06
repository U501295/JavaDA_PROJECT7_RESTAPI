package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Service permettant d'effectuer les actions CRUDs sur les entités user dans l'application.
 * <p>
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        log.debug("user : get all");
        return users;
    }

    public User saveUser(User user) {
        log.debug("user : save");
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        log.debug("user : find by id");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        return user;
    }

    public void deleteUser(Long id) {
        log.debug("user : delete");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);

    }
}
