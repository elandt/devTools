package com.elandt.lil.ec.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

/**
 * Handles all Exceptions in all controllers
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the the return object - good practice so that proprietary information isn't exposed via exceptions
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, options);
                // Always remove exception regardless of what the super implementation does
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied.");
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public void handleHttpServerErrorException(HttpServerErrorException e, HttpServletResponse response) throws IOException {
        response.sendError(e.getStatusCode().value(), e.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public void handleAccessDeniedException(InsufficientAuthenticationException e, HttpServletResponse response) throws IOException {
        LOGGER.error("Handled Insufficient Authentication Exception.", e);
        response.sendError(HttpStatus.FORBIDDEN.value(), "Insufficient Authentication.");
    }

    @ExceptionHandler(Exception.class)
    public void handleAccessDeniedException(Exception e, HttpServletResponse response) throws IOException {
        LOGGER.error("Handled Interal Error Exception", e);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong.");
    }
}
