package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userTest() {

        User user = new User("username","password", "fullname", "role");

        // Save
        user = userRepository.save(user);
        Assert.assertNotNull(user.getId());
        //Assert.assertTrue(trade.getAccount().equals("Trade Account"));
        Assertions.assertThat(user.getUsername()).isEqualTo("username");

        // Update
        user.setFullname("Fullname Update");
        user = userRepository.save(user);
        Assertions.assertThat(user.getFullname()).isEqualTo("Fullname Update");
        //Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

        // Find
        List<User> listResult = userRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Long id = user.getId();
        userRepository.delete(user);
        Optional<User> tradeList = userRepository.findById(id);
        Assert.assertFalse(tradeList.isPresent());

    }
}
