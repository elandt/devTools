package com.elandt.lil.university.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Department of the University
 */
@Entity
@Table
@Getter
@Setter
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

    @Override
    public String toString() {
        return "Department{" +
                "chair=" + chair +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
