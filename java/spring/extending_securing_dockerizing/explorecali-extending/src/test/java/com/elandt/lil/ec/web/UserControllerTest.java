package com.elandt.lil.ec.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elandt.lil.ec.domain.Role;
import com.elandt.lil.ec.domain.User;
import com.elandt.lil.ec.service.UserService;
import com.elandt.lil.ec.web.dto.LoginDto;
import com.elandt.lil.ec.web.helper.JwtRequestHelper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private LoginDto signupDto = new LoginDto("larry", "1234", "larry", "miller");
    private User user = new User(signupDto.getUsername(), signupDto.getPassword(), new Role(),
            signupDto.getFirstName(), signupDto.getLastName());

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtRequestHelper jwtRequestHelper;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllUsers() {
        when(userService.getAll()).thenReturn(Arrays.asList(user));

        ResponseEntity<List<User>> response = restTemplate.exchange("/users",
                HttpMethod.GET,
                new HttpEntity<>(jwtRequestHelper.withRole("ROLE_ADMIN")),
                new ParameterizedTypeReference<List<User>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUsername(), response.getBody().get(0).getUsername());
    }

    @Test
    void testLogin() {
        restTemplate.postForEntity("/users/signin", new LoginDto("admin", "myPass"), Void.class);
        verify(this.userService).signin("admin","myPass");
    }

    @Test
    void testSignup() {
        when(userService.signup(signupDto.getUsername(), signupDto.getPassword(), signupDto.getFirstName(),
                signupDto.getLastName())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = restTemplate.exchange("/users/signup",
                HttpMethod.POST,
                new HttpEntity<>(signupDto,jwtRequestHelper.withRole("ROLE_ADMIN")),
                User.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUsername(), response.getBody().getUsername());
        assertEquals(user.getFirstName(), response.getBody().getFirstName());
        assertEquals(user.getLastName(), response.getBody().getLastName());
        assertEquals(user.getRoles().size(), response.getBody().getRoles().size());
    }

    @Test
    void testSignupUnauthorized() {
        ResponseEntity<User> response = restTemplate.exchange("/users/signup",
                HttpMethod.POST,
                new HttpEntity<>(signupDto,jwtRequestHelper.withRole("ROLE_CSR")),
                User.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
