package com.elandt.lil.ec.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.elandt.lil.ec.domain.User;
import com.elandt.lil.ec.service.UserService;
import com.elandt.lil.ec.web.dto.LoginDto;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(path = "/signin")
    public String login(@RequestBody @Valid LoginDto loginDto) {
        return userService.signin(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(() ->
                new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login failed"));
    }

    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User signup(@RequestBody @Valid LoginDto loginDto) {
        return userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(),
                loginDto.getLastName()).orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists."));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}
