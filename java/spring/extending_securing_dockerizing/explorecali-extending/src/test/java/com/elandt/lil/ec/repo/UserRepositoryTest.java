package com.elandt.lil.ec.repo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elandt.lil.ec.domain.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsernameSuccess() {
        Optional<User> user = userRepository.findByUsername("admin");
        assertTrue(user.isPresent());
    }

    @Test
    void testFindByUsernameFailToFind() {
        Optional<User> user = userRepository.findByUsername("nobody");
        assertFalse(user.isPresent());
    }

}
