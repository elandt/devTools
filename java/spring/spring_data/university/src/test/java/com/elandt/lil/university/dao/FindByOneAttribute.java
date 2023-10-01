package com.elandt.lil.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.elandt.lil.university.PersistenceJPAConfig;
import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Course;
import com.elandt.lil.university.domain.Staff;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class })
public class FindByOneAttribute {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    private List<Staff> allStaff;
    @Test
    public void findByOneAttribute() {
        // Test Create
        UniversityFactory.fillUniversity(universityService);

        studentDao.findByFullTime(true).stream().forEach(s -> assertTrue(s.isFullTime()));

        studentDao.findByAge(20).stream().forEach(student -> assertTrue(student.getAge() == 20));

        studentDao.findByLastName("King").stream()
                .forEach(s -> assertTrue(s.getAttendee().getLastName().equals("King")));


        List<Course> allCourses = universityService.findAllCourses();
        Course firstCourse = allCourses.get(0);

        assertEquals(firstCourse, courseDao.findByName(firstCourse.getName()).get());

        assertEquals(firstCourse.getDepartment().getChair().getMember().getLastName(),
                courseDao.findByChairLastName(firstCourse.getDepartment().getChair().getMember().getLastName())
                    .get(0).getDepartment().getChair().getMember().getLastName());

        Course courseWithPrerequisites = allCourses.stream().filter(x->x.getPrerequisites().size() > 0).findFirst().get();
        Integer prerequisiteId = courseWithPrerequisites.getPrerequisites().get(0).getId();
        assertTrue(courseDao.findCourseByPrerequisite(prerequisiteId).contains(courseWithPrerequisites));

        courseDao.findByCredits(3).stream().forEach(x-> assertEquals(3, x.getCredits()));
    }
}
