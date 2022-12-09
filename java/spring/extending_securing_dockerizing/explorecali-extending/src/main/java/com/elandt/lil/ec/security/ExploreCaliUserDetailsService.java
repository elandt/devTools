package com.elandt.lil.ec.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.elandt.lil.ec.domain.User;
import com.elandt.lil.ec.repo.UserRepository;

@Component
public class ExploreCaliUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public ExploreCaliUserDetailsService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with name %s does not exist", username)));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public Optional<UserDetails> loadByJwt(String token) {
        if (jwtProvider.isValidToken(token)) {
            return Optional.of(
                org.springframework.security.core.userdetails.User.withUsername(jwtProvider.getUsername(token))
                .password("") // token does not have a password, but this field cannot be empty
                .authorities(jwtProvider.getRoles(token))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build()
            );
        }
        return Optional.empty();
    }

    public Optional<UserDetails> loadUserByJwtAndDatabase(String token) {
        if (jwtProvider.isValidToken(token)) {
            return Optional.of(loadUserByUsername(jwtProvider.getUsername(token)));
        }
        return Optional.empty();
    }
}
