package com.elandt.lil.university.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Course;
import com.elandt.lil.university.domain.Staff;

@SpringBootTest
public class FindByOneAttribute {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    private List<Staff> allStaff;
    @Test
    public void findByOneAttribute() {
        // Test Create
        UniversityFactory.fillUniversity(universityService);

        studentRepo.findByFullTime(true).stream().forEach(s -> assertTrue(s.isFullTime()));

        studentRepo.findByAge(20).stream().forEach(student -> assertTrue(student.getAge() == 20));

        studentRepo.findByAttendeeLastName("King").stream()
                .forEach(s -> assertTrue(s.getAttendee().getLastName().equals("King")));


        List<Course> allCourses = universityService.findAllCourses();
        Course firstCourse = allCourses.get(0);

        assertEquals(firstCourse, courseRepo.findByName(firstCourse.getName()).get());

        assertEquals(firstCourse.getDepartment().getChair().getMember().getLastName(),
                courseRepo.findByDepartmentChairMemberLastName(firstCourse.getDepartment().getChair().getMember().getLastName())
                    .get(0).getDepartment().getChair().getMember().getLastName());

        Course courseWithPrerequisites = allCourses.stream().filter(x->x.getPrerequisites().size() > 0).findFirst().get();
        Course prerequisite = courseWithPrerequisites.getPrerequisites().get(0);
        assertTrue(courseRepo.findByPrerequisites(prerequisite).contains(courseWithPrerequisites));

        courseRepo.findByCredits(3).stream().forEach(x-> assertEquals(3, x.getCredits()));
    }
}
