package com.nnk.springboot.tools;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PasswordEncodeTest {

    @Autowired

    private PasswordEncoder passwordEncoder;

    @Test
    public void testPassword() {
        String pw = passwordEncoder.encode("Useruser1!");
        System.out.println("[ " + pw + " ]");
        Assertions.assertThat(pw).isNotEqualTo("admin");

    }
}
