package com.elandt.lil.ec.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean {

    private static final Logger LOGGER = LogManager.getLogger(JwtFilter.class);
    private static final String BEARER = "Bearer";

    private final ExploreCaliUserDetailsService userDetailsService;

    public JwtFilter(ExploreCaliUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Determine if there is a JWT as part of the HTTP Request Header.
     * If it is valid, then set the current context with the Authentication(user and roles) found in the token
     *
     * @param request Servlet Request
     * @param response Servlet Response
     * @param chain Filter Chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Process request to check for a JSON Web Token.");
        // Check for Authorization:Bearer JWT
        String headerValue = ((HttpServletRequest) request).getHeader("Authorization");
        getBearerToken(headerValue).ifPresent(token -> {
            // Pull the username and rols from the JWT to construct the user details
            userDetailsService.loadByJwt(token).ifPresent(userDetails -> {
                // Add the user details (permissions) to the Context for only this API invocation
                SecurityContextHolder.getContext().setAuthentication(
                    new PreAuthenticatedAuthenticationToken(userDetailsService, "", userDetails.getAuthorities()));
            });
        });

        // Move to the next filter in the chain
        chain.doFilter(request, response);
    }

    /**
     * Extract the JWT from the "Bearer <jwt>" header value, if present.
     *
     * @param headerValue header String from HttpServletRequest
     * @return jwt if present, empty otherwise
     */
    private Optional<String> getBearerToken(String headerValue) {
        if (headerValue != null && headerValue.startsWith(BEARER)) {
            return Optional.of(headerValue.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }
}
