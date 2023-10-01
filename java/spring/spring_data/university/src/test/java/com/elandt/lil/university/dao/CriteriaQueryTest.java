package com.elandt.lil.university.dao;

import static com.elandt.lil.university.business.CourseFilter.filterBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.elandt.lil.university.PersistenceJPAConfig;
import com.elandt.lil.university.business.CourseFilter;
import com.elandt.lil.university.business.DynamicQueryService;
import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Department;
import com.elandt.lil.university.domain.Staff;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class })
public class CriteriaQueryTest {

    @Autowired
    private DynamicQueryService queryService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private StaffDao staffDao;

    @Test
    void findByCriteria() {
        UniversityFactory.fillUniversity(universityService);
        Department humanities = departmentDao.findByName("Humanities").get();
        Staff professorBlack = staffDao.findByLastName("Black").stream().findFirst().get();

        System.out.println('\n' + "*** All Humanities Courses");
        queryAndVerify(filterBy().department(humanities));

        System.out.println('\n' + "*** 4 credit courses");
        queryAndVerify(filterBy().credits(4));

        System.out.println('\n' + "*** Courses taught by Professor Black");
        queryAndVerify(filterBy().instructor(professorBlack));

        System.out.println('\n' + "*** Courses In Humanties, taught by Professor Black, 4 credits");
        queryAndVerify(filterBy()
                .department(humanities)
                .credits(4)
                .instructor(professorBlack));
    }


    private void queryAndVerify(CourseFilter filter) {
        queryService.findCoursesByCriteria(filter)
                .forEach(course -> {
                    filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
                    filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
                    filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
                    System.out.println(course);
                });
    }
}
