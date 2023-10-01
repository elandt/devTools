package com.elandt.lil.university.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Student at the University
 */
@Entity
@Table
@Getter
@ToString
@EqualsAndHashCode
public class Student {

    @Id
    @GeneratedValue
    private Integer studentId;

    @Embedded
    private Person attendee;

    @Column
    private boolean fullTime;

    @Column
    private Integer age;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    public Student(Person attendee, boolean fullTime, Integer age) {
        this.attendee = attendee;
        this.fullTime = fullTime;
        this.age = age;
        courses = new ArrayList<>();
    }

    protected Student() {
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
