package com.elandt.lil.university.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.elandt.lil.university.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course>{

    Optional<Course> findByName(String name);
    List<Course> findByPrerequisites(Course course);
    List<Course> findByCredits(int credits);
    List<Course> findByDepartmentChairMemberLastName(String chair);
}
