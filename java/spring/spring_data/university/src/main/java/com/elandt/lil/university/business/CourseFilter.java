package com.elandt.lil.university.business;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import com.elandt.lil.university.domain.Course;
import com.elandt.lil.university.domain.Department;
import com.elandt.lil.university.domain.Staff;

import jakarta.persistence.criteria.Predicate;

/**
 * Helper class to filter courses in the Dynamic Query Service
 */
public class CourseFilter {

    private Optional<Department> department = Optional.empty();
    private Optional<Integer> credits = Optional.empty();
    private Optional<Staff> instructor = Optional.empty();

    public static CourseFilter filterBy(){
        return new CourseFilter();
    }

    public CourseFilter department(Department department) {
        this.department = Optional.of(department);
        return this;
    }
    public CourseFilter credits(Integer credits) {
        this.credits = Optional.of(credits);
        return this;
    }

    public CourseFilter instructor(Staff instructor) {
        this.instructor = Optional.of(instructor);
        return this;
    }

    public Optional<Department> getDepartment() {
        return department;
    }

    public Optional<Integer> getCredits() {
        return credits;
    }

    public Optional<Staff> getInstructor() {
        return instructor;
    }

    public Specification<Course> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            department.ifPresent(d -> predicates.add(criteriaBuilder.equal(root.get("department"), d)));
            credits.ifPresent(c -> predicates.add(criteriaBuilder.equal(root.get("credits"), c)));
            instructor.ifPresent(i -> predicates.add(criteriaBuilder.equal(root.get("instructor"), i)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Example<Course> getExample() {
        // Create the probe
        var course = new Course(null,
                credits.orElse(null),
                instructor.orElse(null),
                department.orElse(null));
        // Return the Example created from the probe
        return Example.of(course);
    }

    // Related to Querydsl dynamic query example
    // public com.querydsl.core.types.Predicate getQueryDslPredicate() {
    //     var predicate = new BooleanBuilder();

    //     department.ifPresent(d -> predicate.and(course.department.eq(d)));
    //     credits.ifPresent(c -> predicate.and(course.credits.eq(c)));
    //     instructor.ifPresent(i -> predicate.and(course.instructor.eq(i)));

    //     return predicate;
    // }
}