package com.elandt.lil.university.domain;

import java.util.Objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;


/**
 * Staff member at the University
 */
@Entity
@Table
@Getter
public class Staff {

    @Id
    @GeneratedValue
    private Integer id;

    @Embedded
    private Person member;

    public Staff(Person member) {
        this.member = member;
    }

    protected Staff() {
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", member=" + member +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id.equals(staff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
