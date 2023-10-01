package com.elandt.lil.university.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Department of the University
 */
@Entity
@Table
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Department {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    private Staff chair;

    @OneToMany
    private List<Course> courses = new ArrayList<>();

    public Department(String name, Staff chair) {
        this.name = name;
        this.chair = chair;
    }

    protected Department() {
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
