package com.elandt.lil.student_service.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.elandt.lil.student_service.domain.Student;
import com.elandt.lil.student_service.exception.StudentNotFoundException;
import com.elandt.lil.student_service.service.StudentService;

// Personally, I wouldn't consider this test class an integration test because it's mocking everything outside the controller
@WebMvcTest
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mocking because `@WebMvcTest` does _not_ load `@Service`, `@Component`, `@Repository` annotated beans
    @MockitoBean
    private StudentService studentService;

    @Test
    void testGetStudent_forSavedStudent_isReturned() throws Exception {
        // given
        given(studentService.findStudentById(1L)).willReturn(Student.builder()
                .id(1L)
                .name("Mark")
                .grade(10)
                .build());

        // when //then
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Mark"))
                .andExpect(jsonPath("grade").value(10));
    }

    @Test
    void testGetStudent_forMissingStudent_status404() throws Exception {
        // given
        given(studentService.findStudentById(anyLong())).willThrow(StudentNotFoundException.class);

        // when //then
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());
    }
}
