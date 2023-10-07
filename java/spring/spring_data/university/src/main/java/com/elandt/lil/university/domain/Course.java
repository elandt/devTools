package com.elandt.lil.university.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Course offered at the University
 */
@Entity
@Table
@Getter
public class Course {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer credits;

    @ManyToOne
    private Staff instructor;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Course> prerequisites = new ArrayList<>();

    @ManyToOne
    private Department department;

    public Course(String name, Integer credits, Staff instructor, Department department) {
        this.name = name;
        this.credits = credits;
        this.instructor = instructor;
        this.department = department;
    }

    protected Course() {
    }

    public Course addPrerequisite(Course prerequisite){
        prerequisites.add(prerequisite);
        return this;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", credits=" + credits +
                ", instructor=" + instructor +
                ", department=" + department.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
