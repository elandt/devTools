package com.elandt.lil.university.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Student;

@SpringBootTest
public class FindByClausesAndExpressions {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StudentRepo studentRepo;

    @Test
    public void findByClausesAndExpressions() {
        // Test Create
        UniversityFactory.fillUniversity(universityService);
        List<Student> students = universityService.findAllStudents();
        Student firstStudent = students.get(0);

        assertTrue(studentRepo.findTopByOrderByAgeDesc().get().getAge() == 22);

        assertEquals(firstStudent, studentRepo.findByAttendeeFirstNameAndAttendeeLastName(firstStudent.getAttendee().getFirstName(),
                firstStudent.getAttendee().getLastName()).get(0));

        studentRepo.findByAgeLessThan(20).stream().forEach(s-> assertTrue(s.getAge() < 20));

        studentRepo.findByAttendeeLastNameLike("%o%")
                .stream().forEach(s->assertTrue(s.getAttendee().getLastName().contains("o")));

        assertTrue(studentRepo.findFirstByOrderByAttendeeLastNameAsc().get().getAttendee().getLastName().equals("Doe"));

        List<Student> students3 = studentRepo.findTop3ByOrderByAgeDesc();
        assertTrue(students3.size() == 3);
        students3.stream().forEach(s -> assertTrue(s.getAge() != 18));
    }

    @Test
    public void testEquivalentQueryPairs() {
        UniversityFactory.fillUniversity(universityService);
        List<Student> students = universityService.findAllStudents();
        Student firstStudent = students.get(0);

        // Check findByAttendeeFirstNameAndAttendeeLastName vs findByFirstAndLastName
        assertEquals(
            studentRepo.findByAttendeeFirstNameAndAttendeeLastName(firstStudent.getAttendee().getLastName(), firstStudent.getAttendee().getLastName()),
            studentRepo.findByFirstAndLastName(firstStudent.getAttendee().getLastName(), firstStudent.getAttendee().getLastName()),
            "findByFirstAndLastName result does NOT match findByAttendeeFirstNameAndAttendeeLastName result");

        // Check findByAttendeeLastNameLike vs findSimilarLastName
        assertEquals(
            studentRepo.findByAttendeeLastNameLike("%o%"),
            studentRepo.findSimilarLastName("%o%"),
            "findSimilarLastName result does NOT match findByAttendeeLastNameLike result");

        // Check findFirstByOrderByAttendeeLastNameAsc vs findFirstInAlphabet
        assertEquals(
            studentRepo.findFirstByOrderByAttendeeLastNameAsc(),
            studentRepo.findFirstInAlphabet(),
            "findFirstInAlphabet result does NOT match findFirstByOrderByAttendeeLastNameAsc result");

        // Check findTopByOrderByAgeDesc vs findOldest
        assertEquals(
            studentRepo.findTopByOrderByAgeDesc(),
            studentRepo.findOldest(),
            "findOldest result does NOT match findTopByOrderByAgeDesc result");

        // Check findTop3ByOrderByAgeDesc vs findThreeOldest
        assertEquals(
            studentRepo.findTop3ByOrderByAgeDesc(),
            studentRepo.findThreeOldest(),
            "findThreeOldest result does NOT match findTop3ByOrderByAgeDesc result");
    }
}
