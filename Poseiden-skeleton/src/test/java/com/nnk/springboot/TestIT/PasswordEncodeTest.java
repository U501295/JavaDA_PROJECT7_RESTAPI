package com.nnk.springboot.TestIT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncodeTest {

    @Autowired

    private PasswordEncoder passwordEncoder;

    @Test
    public void testPassword() {
        String pw = passwordEncoder.encode("admin");
        System.out.println("[ " + pw + " ]");
    }
}
