package com.elandt.lil.ec.web.helper;


import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.elandt.lil.ec.domain.Role;
import com.elandt.lil.ec.security.JwtProvider;

@Component
public class JwtRequestHelper {

    private final JwtProvider jwtProvider;

    public JwtRequestHelper(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * Generate Authorization headers with the given role for JWT Authentication
     *
     * @param roleName name of Role to include in Http Header
     * @return HttpHeaders with Content-Type and Authorization set
     */
    public HttpHeaders withRole(String roleName) {
        HttpHeaders headers = new HttpHeaders();
        Role role = new Role();
        role.setRoleName(roleName);
        String token = jwtProvider.createToken("anonymous", Arrays.asList(role));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }
}
