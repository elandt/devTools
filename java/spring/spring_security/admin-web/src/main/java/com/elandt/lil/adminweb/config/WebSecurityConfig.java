package com.elandt.lil.adminweb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize ->
                // Open the / and /home endpoints to anyone
                authorize.requestMatchers("/", "/home", "/login").permitAll()
                        // Open /customers and all sub-pages to anyone with the "USER" role
                        .requestMatchers("/customers/**").hasRole("USER")
                        .requestMatchers("/orders/**").hasRole("ADMIN")
                        // Require auth for everything else
                        .anyRequest().authenticated())
                // Can implement basic auth with the following line
                // .httpBasic(Customizer.withDefaults())
                // The next two blocks define the pieces needed for using form-based auth
                .formLogin(login ->
                    login.loginPage("/login")
                            .failureUrl("/login?error")
                            .permitAll()
                ).logout(logout ->
                    logout.clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .logoutSuccessUrl("/login?logout")
                            .permitAll()
                );
        return httpSecurity.build();
    }

    // Used for the LDAP auth section of the course
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=landon,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");
    }

    @Bean
    public UserDetailsService users (DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        var authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        return authorityMapper;
    }
}
