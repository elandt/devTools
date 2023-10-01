package com.elandt.lil.university.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elandt.lil.university.dao.CourseDao;
import com.elandt.lil.university.dao.DepartmentDao;
import com.elandt.lil.university.dao.StaffDao;
import com.elandt.lil.university.dao.StudentDao;
import com.elandt.lil.university.domain.Course;
import com.elandt.lil.university.domain.Department;
import com.elandt.lil.university.domain.Person;
import com.elandt.lil.university.domain.Staff;
import com.elandt.lil.university.domain.Student;

@Service
public class UniversityService {

    private DepartmentDao departmentDao;

    private StaffDao staffDao;

    private StudentDao studentDao;

    private CourseDao courseDao;

    public UniversityService(CourseDao courseDao, DepartmentDao departmentDao, StaffDao staffDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.departmentDao = departmentDao;
        this.staffDao = staffDao;
        this.studentDao = studentDao;
    }

    public Student createStudent(String firstName, String lastName, boolean fullTime, int age) {
        return studentDao.save(new Student(new Person(firstName, lastName), fullTime, age));
    }

    public Staff createFaculty(String firstName, String lastName) {
        return staffDao.save(new Staff(new Person(firstName, lastName)));
    }

    public Department createDepartment(String deptname, Staff deptChair) {
        return departmentDao.save(new Department(deptname, deptChair));
    }

    public Course createCourse(String name, int credits, Staff professor, Department department) {
        return courseDao.save(new Course(name, credits, professor, department));
    }

    public Course createCourse(String name, int credits, Staff professor, Department department, Course... prereqs) {
        Course c = new Course(name, credits, professor, department);
        for (Course p : prereqs) {
            c.addPrerequisite(p);
        }
        return courseDao.save(c);
    }

    public List<Course> findAllCourses() {
        return courseDao.findAll();
    }

    public List<Staff> findAllStaff() {
        return staffDao.findAll();
    }

    public List<Department> findAllDepartments() {
        return departmentDao.findAll();
    }

    public List<Student> findAllStudents() {
        return studentDao.findAll();
    }

    public void deleteAll() {
        try {
            studentDao.deleteAll();
            courseDao.deleteAll();
            departmentDao.deleteAll();
            staffDao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
