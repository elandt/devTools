package com.elandt.lil.ec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Course video (as of 12/3/2022) extend WebSecurityConfigurerAdapter, but it's deprecated because it's not safe for production use.
// Using SecurityFilterChain and WebSecurityCustomizer instead, per WebSecurityConfigurerAdapter Javadoc
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final ExploreCaliUserDetailsService userDetailsService;

    public WebSecurityConfiguration(ExploreCaliUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize ->
                // Open the packages, tours, ratings, and signin APIs
                authorize.antMatchers("/packages/**").permitAll()
                        .antMatchers("/tours/**").permitAll()
                        .antMatchers("/ratings/**").permitAll()
                        .antMatchers("/users/signin").permitAll()
                        // Require auth for everything else
                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Allow swagger to be accessed without authentication
        return (webSecurity) -> webSecurity.ignoring()
                .antMatchers("/v3/api-docs/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public");
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
