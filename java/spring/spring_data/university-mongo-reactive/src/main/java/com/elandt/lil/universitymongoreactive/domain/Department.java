package com.elandt.lil.universitymongoreactive.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@NoArgsConstructor
@Getter
@Setter
public class Department {

    @Id
    private String id;

    private String name;

    private Staff chair;

    public Department(String name, Staff chair) {
        this.name = name;
        this.chair = chair;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chair=" + chair +
                '}';
    }
}
