package com.wang.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("12345"));
        System.out.println(passwordEncoder.encode("12345"));
        System.out.println(passwordEncoder.encode("12345"));
        System.out.println(passwordEncoder.matches("12345","$2a$10$i31lthhWrYvCraF/bkpM1eJLjIlT7/sVmDqcDRarxTbvuf6jI74Wq"));
    }

}
