package com.elandt.lil.student_service.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
// This is another way to have this type of exception return a 404 when it occurs when calling an endpoint
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends NoSuchElementException {

    private final String message;

    public StudentNotFoundException(String message) {
        this.message = message;
    }

}
