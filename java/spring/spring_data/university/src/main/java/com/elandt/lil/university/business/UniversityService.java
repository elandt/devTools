package com.elandt.lil.university.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elandt.lil.university.domain.Course;
import com.elandt.lil.university.domain.Department;
import com.elandt.lil.university.domain.Person;
import com.elandt.lil.university.domain.Staff;
import com.elandt.lil.university.domain.Student;
import com.elandt.lil.university.repo.CourseRepo;
import com.elandt.lil.university.repo.DepartmentRepo;
import com.elandt.lil.university.repo.StaffRepo;
import com.elandt.lil.university.repo.StudentRepo;

@Service
public class UniversityService {

    private DepartmentRepo departmentRepo;

    private StaffRepo staffRepo;

    private StudentRepo studentRepo;

    private CourseRepo courseRepo;

    public UniversityService(CourseRepo courseRepo, DepartmentRepo departmentRepo, StaffRepo staffRepo, StudentRepo studentRepo) {
        this.courseRepo = courseRepo;
        this.departmentRepo = departmentRepo;
        this.staffRepo = staffRepo;
        this.studentRepo = studentRepo;
    }

    public Student createStudent(String firstName, String lastName, boolean fullTime, int age) {
        return studentRepo.save(new Student(new Person(firstName, lastName), fullTime, age));
    }

    public Staff createFaculty(String firstName, String lastName) {
        return staffRepo.save(new Staff(new Person(firstName, lastName)));
    }

    public Department createDepartment(String deptname, Staff deptChair) {
        return departmentRepo.save(new Department(deptname, deptChair));
    }

    public Course createCourse(String name, int credits, Staff professor, Department department) {
        return courseRepo.save(new Course(name, credits, professor, department));
    }

    public Course createCourse(String name, int credits, Staff professor, Department department, Course... prereqs) {
        Course c = new Course(name, credits, professor, department);
        for (Course p : prereqs) {
            c.addPrerequisite(p);
        }
        return courseRepo.save(c);
    }

    public List<Course> findAllCourses() {
        return courseRepo.findAll();
    }

    public List<Staff> findAllStaff() {
        return staffRepo.findAll();
    }

    public List<Department> findAllDepartments() {
        return departmentRepo.findAll();
    }

    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    public void deleteAll() {
        try {
            studentRepo.deleteAll();
            courseRepo.deleteAll();
            departmentRepo.deleteAll();
            staffRepo.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
