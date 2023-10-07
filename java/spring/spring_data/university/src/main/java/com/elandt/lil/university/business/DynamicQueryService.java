package com.elandt.lil.university.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elandt.lil.university.domain.Course;
import com.elandt.lil.university.repo.CourseRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DynamicQueryService {

    private final CourseRepo courseRepo;

    public List<Course> filterBySpecification(CourseFilter courseFilter) {
        return courseRepo.findAll(courseFilter.getSpecification());
    }

    public List<Course> filterByExample(CourseFilter courseFilter) {
        return courseRepo.findAll(courseFilter.getExample());
    }

    // Related to Querydsl dynamic query example
    // private final CourseQueryDslRepo courseQueryDslRepo;

    // public List<Course> filterByQueryDsl(CourseFilter courseFilter) {
    //     var courses = new ArrayList<Course>();
    //     courseQueryDslRepo.findAll().forEach(courses::add);
    //     return courses;
    // }
}
