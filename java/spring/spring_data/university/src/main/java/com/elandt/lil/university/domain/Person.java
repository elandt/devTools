package com.elandt.lil.university.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Embeddable first and last name object to reduce code duplication
 */
@Embeddable
@Getter
@Setter
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

    @Override
    public String toString() {
        return  " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + "\' ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
