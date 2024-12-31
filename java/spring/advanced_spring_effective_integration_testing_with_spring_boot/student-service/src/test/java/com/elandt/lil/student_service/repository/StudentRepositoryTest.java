package com.elandt.lil.student_service.repository;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.elandt.lil.student_service.domain.Student;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testFindByName_returnsStudentDetails() {
        // given
        // Using the `studentRepository.save()` call here leads to interacting with the
        // first level cache of the repository rather than with the database. This hides the possible
        // bug of not having a default no-args constructor on the entity. To address this, use Spring's `TestEntityManager`
        // to force flushing to the db. This will reveal the lack of the default constructor as the test will fail.
        var savedStudent = testEntityManager.persistFlushFind(Student.builder()
                .name("Mark")
                .build());

        // when
        var student = studentRepository.findByName("Mark");

        // then
        then(student.getId()).isNotNull();
        then(student.getName()).isEqualTo(savedStudent.getName());
    }

    @Test
    void testFindAverageGradeOfActiveStudents_returnAverageGrade() {
        // given
        var alice = Student.builder()
                .name("Alice")
                .active(true)
                .grade(70)
                .build();
        var bob = Student.builder()
                .name("Bob")
                .active(true)
                .grade(80)
                .build();
        var candice = Student.builder()
                .name("Candice")
                .active(true)
                .grade(90)
                .build();
        var doug = Student.builder()
                .name("David")
                .active(false)
                .grade(70)
                .build();
        var elias = Student.builder()
                .name("Elias")
                .active(false)
                .grade(100)
                .build();
        List.of(alice, bob, candice, doug, elias).forEach(testEntityManager::persistFlushFind);

        // when
        var averageGradeOfActiveStudents = studentRepository.findAverageGradeOfActiveStudents();

        // then
        then(averageGradeOfActiveStudents).isEqualTo(80);
    }
}
