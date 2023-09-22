package com.elandt.lil.bones.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class WebFluxSecurityConfig {

    @Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(authorize ->
                authorize.pathMatchers("/hello").permitAll()
                        .anyExchange().hasRole("ADMIN"))
                .httpBasic(Customizer.withDefaults());
		return http.build();
	}

    @Bean
	public MapReactiveUserDetailsService userDetailsService() {
		var userdetails = new ArrayList<UserDetails>();
		// Do NOT do this in PRODUCTION the `withDefaultPasswordEncoder` is deprecated for a reason!!!
		userdetails.add(User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build());
		userdetails.add(User.withDefaultPasswordEncoder()
				.username("admin")
				.password("password")
				.roles("USER", "ADMIN")
				.build());
		return new MapReactiveUserDetailsService(userdetails);
	}
}
