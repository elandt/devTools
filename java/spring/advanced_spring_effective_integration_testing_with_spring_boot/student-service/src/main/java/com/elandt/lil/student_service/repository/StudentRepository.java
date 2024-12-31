package com.elandt.lil.student_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.elandt.lil.student_service.domain.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByName(String name);

    @Query(value = "Select avg(s.grade) from Student s where s.active = true")
    Double findAverageGradeOfActiveStudents();
}
