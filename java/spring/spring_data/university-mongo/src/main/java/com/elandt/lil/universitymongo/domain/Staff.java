package com.elandt.lil.universitymongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Document
@NoArgsConstructor
@Getter
public class Staff {

    @Id
    private String id;

    private Person member;

    public Staff(Person member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", member=" + member +
                '}';
    }
}
