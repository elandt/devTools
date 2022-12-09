package com.elandt.lil.ec.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elandt.lil.ec.domain.User;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void testSignupEncodesPassword() {
        Optional<User> user = userService.signup("dummyUsername", "dummyPassword", "John", "Doe");
        assertNotEquals("dummyPassword", user.get().getPassword());
    }
}
