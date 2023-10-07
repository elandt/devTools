package com.elandt.lil.universitymongoreactive.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Person {

    private String firstName;

    private String lastName;

    @Override
    public String toString() {
        return " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + "\' ";
    }

}
