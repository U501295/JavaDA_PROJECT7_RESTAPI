package com.nnk.springboot.controllers.api;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/user")

public class UserRestController {
    @Autowired
    private UserService userService;


    @GetMapping()
    public List<User> getUsers() {
        log.debug("get all users");
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            User userEntity = userService.findUserById(id);
            log.debug("successfully get user/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(userEntity);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while getting user because of missing user with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postUser(@Valid @RequestBody User userEntity,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while posting user because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        User userEntitySaved = userService.saveUser(userEntity);
        log.debug("successfully post user");
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntitySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putUser(@PathVariable Long id,
                                     @Valid @RequestBody User userEntity,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while putting user because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        try {
            User userEntityToModify = userService.findUserById(id);
            userEntityToModify = userEntity;
            userEntityToModify.setId(id);
            User userSaved = userService.saveUser(userEntity);
            log.debug("successfully put user/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(userSaved);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while putting user because missing user with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            String logAndBodyMessage = "successfully delete user/" + id;
            log.debug(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(logAndBodyMessage);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while deleting user because of missing user with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }
}
