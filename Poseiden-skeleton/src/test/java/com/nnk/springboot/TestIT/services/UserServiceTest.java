package com.nnk.springboot.TestIT.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test(expected = IllegalArgumentException.class)
    public void userTest() {
        User user = new User("username", "password", "fullname", "role");

        // Save
        user = userService.saveUser(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(user.getUsername(), "username", "username");

        // Update
        user.setUsername("Pablo");
        user = userService.saveUser(user);
        Assert.assertEquals(user.getUsername(), "Pablo", "Pablo");

        // FindAll
        List<User> listResult = userService.findAllUsers();
        Assert.assertTrue(listResult.size() > 0);

        // FindOne
        Long id = user.getId();
        User userToBeFound = userService.findUserById(id);
        Assert.assertTrue(userToBeFound.getId() > 0);

        // Delete
        userService.deleteUser(id);
        User userException = userService.findUserById(id);

    }


}
