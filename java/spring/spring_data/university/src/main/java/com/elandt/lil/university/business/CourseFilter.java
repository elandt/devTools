package com.elandt.lil.university.business;

import java.util.Optional;

import com.elandt.lil.university.domain.Department;
import com.elandt.lil.university.domain.Staff;

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
}