package com.elandt.lil.university.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Embeddable first and last name object to reduce code duplication
 */
@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Person {

    @Column
    private String firstName;

    @Column
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected Person() {
    }
}
