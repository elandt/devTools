package com.elandt.lil.university.repo;

import static com.elandt.lil.university.business.CourseFilter.filterBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.elandt.lil.university.business.CourseFilter;
import com.elandt.lil.university.business.DynamicQueryService;
import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Department;
import com.elandt.lil.university.domain.Person;
import com.elandt.lil.university.domain.Staff;

@SpringBootTest
public class CriteriaQueryTest {

    @Autowired
    private DynamicQueryService queryService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private StaffRepo staffRepo;

    @Test
    void findByCriteria() {
        UniversityFactory.fillUniversity(universityService);
        Department humanities = departmentRepo.findByName("Humanities").get();
        Staff professorBlack = staffRepo.findByLastName("Black").stream().findFirst().get();

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

    @Test
    public void testQueryMethodVsQueryByExample() {
        UniversityFactory.fillUniversity(universityService);
        Department humanities = departmentRepo.findByName("Humanities").get();
        var humanitiesExample = Example.of(
                new Department("humanities", null),
                ExampleMatcher
                        .matching()
                        .withIgnoreCase());
        Department humanitiesQBE = departmentRepo.findOne(humanitiesExample).get();

        Staff professorBlack = staffRepo.findByLastName("Black").stream().findFirst().get();
        var professorBlackExample = Example.of(
                new Staff(new Person(null, "black")),
                ExampleMatcher
                        .matching()
                        .withIgnoreCase());
        Staff professorBlackQBE = staffRepo.findAll(professorBlackExample).stream().findFirst().get();

        assertEquals(humanities, humanitiesQBE, "humanitiesQBE does not match humanities");
        assertEquals(professorBlack, professorBlackQBE, "professorBlackQBE does not match professorBlack");
    }


    private void queryAndVerify(CourseFilter filter) {
        queryService.filterBySpecification(filter)
                .forEach(course -> {
                    filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
                    filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
                    filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
                    System.out.println(course);
                });

        // Demostrates using QueryByExample
        // Not necessary to have both this and the above, but keeping to show that they achieve the same results
        queryService.filterByExample(filter)
                .forEach(course -> {
                    filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
                    filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
                    filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
                    System.out.println(course);
                });

        // Related to Querydsl dynamic query example
        // queryService.filterByQueryDsl(filter)
        //         .forEach(course -> {
        //             filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
        //             filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
        //             filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
        //             System.out.println(course);
        //         });
    }

    @Test
    public void testEquivalentQueries() {
        UniversityFactory.fillUniversity(universityService);
        assertEquals(
                staffRepo.findByMemberLastName("Black").stream().findFirst().get(),
                staffRepo.findByLastName("Black").stream().findFirst().get(),
                "findByLastName result does NOT match findByMemberLastName result");
    }
}
