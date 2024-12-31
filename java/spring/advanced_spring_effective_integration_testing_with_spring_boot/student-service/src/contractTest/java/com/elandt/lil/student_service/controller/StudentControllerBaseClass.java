package com.elandt.lil.student_service.controller;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.elandt.lil.student_service.domain.Student;
import com.elandt.lil.student_service.service.StudentService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest
class StudentControllerBaseClass {

    @Autowired
    private MockMvc mockMvc;

    // Mocking because `@WebMvcTest` does _not_ load `@Service`, `@Component`, `@Repository` annotated beans
    @MockitoBean
    private StudentService studentService;

    @BeforeEach
    void before() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        // given
        given(studentService.findStudentById(1L)).willReturn(Student.builder()
                .id(1L)
                .name("Mark")
                .grade(10)
                .build());
    }
}
