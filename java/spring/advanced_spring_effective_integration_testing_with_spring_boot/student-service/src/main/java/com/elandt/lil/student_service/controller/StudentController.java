package com.elandt.lil.student_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.student_service.domain.Student;
import com.elandt.lil.student_service.service.StudentService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/students/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    // @ExceptionHandler
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // void handleStudentNotFoundException(StudentNotFoundException e) {
    //     // This is one way to have the `StudentNotFoundException` exception thrown by the `studentService` call return a 404 status
    // }

}
