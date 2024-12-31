package com.elandt.lil.student_client.client;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import com.elandt.lil.student_client.domain.Student;

@SpringBootTest
@AutoConfigureStubRunner(ids = "com.elandt.lil:student-service:+:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class StudentClientTest {

    @Autowired
    private StudentClient studentClient;

    @Test
    void testGetStudent_forGivenStudent_isReturned() {
        // given
        var id = 1L;

        // when
        var student = studentClient.getStudent(id);

        // then
        then(student).extracting(Student::getId, Student::getName, Student::getGrade)
                .containsExactly(1L, "Mark", 10);
    }
}
