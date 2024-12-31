package com.elandt.lil.student_service.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.elandt.lil.student_service.domain.Student;
import com.elandt.lil.student_service.repository.StudentRepository;

// We're testing from the service layer down to the data layer, so we don't need the web layer loaded
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class StudentCacheTest {

    @Autowired
    private StudentService studentService;

    @MockitoBean
    private StudentRepository studentRepository;

    @Test
    void testFindStudentById_forMultipleRequests_isRetrievedFromCache() {
        // given
        var id = 123L;
        given(studentRepository.findById(id))
                .willReturn(Optional.of(
                        Student.builder()
                                .id(id)
                                .name("Mark")
                                .build()
                ));

        // when
        studentService.findStudentById(id);
        studentService.findStudentById(id);
        studentService.findStudentById(id);

        // then
        // NOTE: this is the Mockito BDDMockito `then` _not_ the AssertJ BDDAssertions `then`
        then(studentRepository).should(times(1)).findById(id);
    }

}
