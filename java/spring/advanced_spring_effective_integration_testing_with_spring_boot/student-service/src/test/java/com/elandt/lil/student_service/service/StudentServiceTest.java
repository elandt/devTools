package com.elandt.lil.student_service.service;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.elandt.lil.student_service.domain.Student;
import com.elandt.lil.student_service.exception.StudentNotFoundException;
import com.elandt.lil.student_service.repository.StudentRepository;

import jakarta.transaction.Transactional;

// We're testing from the service layer down to the data layer, so we don't need the web layer loaded
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
// Not included in @SpringBootTest like it is in @DataJpaTest slice - so interations would not be
// rolled back automatically when using only @SpringbootTest - without this you could run into
// side effects that impact other tests
@Transactional
class StudentServiceTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @DisplayName("Returning saved student from service layer")
    @Test
    void testFindStudentById_forSavedStudent_isReturned() {
        // given
        var savedStudent = studentRepository.save(Student.builder()
                .name("Mark")
                .grade(70)
                .active(true)
                .build());

        // when
        var student = studentService.findStudentById(savedStudent.getId());

        // then
        then(student).usingRecursiveComparison().isEqualTo(savedStudent);
    }

    @DisplayName("StudentNotFoundException is thrown when no Student is found for the given id")
    @Test
    void testFindStudentById_forNonExistentId_throwsStudentNotFoundException() {
        // given
        var id = 1000L;

        // when/then (slightly less BDD-style as the "when" is in the middle as the `isThrownBy()`)
        thenExceptionOfType(StudentNotFoundException.class)
                .isThrownBy(() -> studentService.findStudentById(id))
                .withMessageContaining("No Student found with id: " + id);
    }

    // The same test as above, but a more standard BDD style
    @DisplayName("BDD-style: StudentNotFoundException is thrown when no Student is found for the given id")
    @Test
    void testFindStudentById_forNonExistentId_throwsStudentNotFoundException_withBDDStyleTestFormat() {
        // given
        var id = 1000L;

        // when
        var thrown = catchThrowable(() -> studentService.findStudentById(id));

        //then
        then(thrown)
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("No Student found with id: " + id);
    }
}
