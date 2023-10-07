package com.elandt.lil.university.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elandt.lil.university.domain.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    List<Student> findByFullTime(boolean fullTime);
    List<Student> findByAge(Integer age);
    List<Student> findByAttendeeLastName(String lastName);
    List<Student> findByAgeLessThan(int maxAge);

    /* ************************************ *
    * The below are equivalent query pairs *
    * ************************************ */
    List<Student> findByAttendeeFirstNameAndAttendeeLastName(String firstName, String lastName);
    @Query("SELECT s FROM Student s WHERE s.attendee.firstName = :firstName and s.attendee.lastName = :lastName")
    List<Student> findByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<Student> findByAttendeeLastNameLike(String nameCriteria);
    @Query("SELECT s FROM Student s where s.attendee.lastName like :nameCriteria")
    List<Student> findSimilarLastName(@Param("nameCriteria") String nameCriteria);

    // Find the student with the last name that comes first alphabetically
    Optional<Student> findFirstByOrderByAttendeeLastNameAsc();
    @Query(value = "SELECT * FROM student s ORDER BY s.last_Name ASC LIMIT 1", nativeQuery = true)
    Optional<Student> findFirstInAlphabet();

    // Find the oldest Student
    Optional<Student> findTopByOrderByAgeDesc();
    @Query(value = "SELECT * FROM student s ORDER BY s.age DESC LIMIT 1", nativeQuery = true)
    Optional<Student> findOldest();

    // Find 3 oldest Students
    List<Student> findTop3ByOrderByAgeDesc();
    @Query(value = "SELECT * FROM student s ORDER BY s.age DESC LIMIT 3", nativeQuery = true)
    List<Student> findThreeOldest();
}
