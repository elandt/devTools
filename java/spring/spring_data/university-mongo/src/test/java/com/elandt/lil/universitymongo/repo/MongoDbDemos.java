package com.elandt.lil.universitymongo.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.elandt.lil.universitymongo.domain.Department;
import com.elandt.lil.universitymongo.domain.Person;
import com.elandt.lil.universitymongo.domain.Staff;

@DataMongoTest
@Testcontainers
public class MongoDbDemos {

    @Container
    @ServiceConnection
    static final MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:7.0.2"));

    @Autowired
    StaffRepo staffRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Test
    public void mongoQueryMethods() {
        Staff deanJones = createStaff("John", "Jones");
        Staff deanMartin = createStaff("Matthew", "Martin");
        Staff profBrown = createStaff("James", "Brown");
        Staff profMiller = createStaff("Judy", "Miller");
        Staff profDavis = createStaff("James", "Davis");
        Staff profMoore = createStaff("Allison", "Moore");
        Staff profThomas = createStaff("Tom", "Thomas");
        Staff profGreen = createStaff("Graham", "Green");
        Staff profWhite = createStaff("Whitney", "White");
        Staff profBlack = createStaff("Jack", "Black");
        Staff profKing = createStaff("Queen", "King");
        //Departments
        Department humanities = createDepartment("Humanities", deanJones);
        Department naturalSciences = createDepartment("Natural Sciences", deanMartin);
        Department socialSciences = createDepartment( "Social Sciences", deanJones);


        //Paging and Sorting Queries
        System.out.println("\nFind all staff members, sort alphabetically by last name");
        Sort sortByLastName = Sort.by(Sort.Direction.ASC, "member.lastName");
        staffRepo.findAll(sortByLastName).forEach(System.out::println);

        System.out.println("\nFind first 5 Staff members, sort alphabetically by last name");
        Page<Staff> members = staffRepo.findAll(PageRequest.of(0, 5, sortByLastName));
        members.forEach(System.out::println);


        //Property Expression
        System.out.println("\nFind all staff members with last name King");
        staffRepo.findByMemberLastName("King").forEach(System.out::println);

        //@Query with JSON query string
        //"{ 'member.firstName' : ?0 }"
        System.out.println("\nFind all staff members with first name John");
        staffRepo.findByFirstName("John").forEach(System.out::println);


        //***************Department query methods***************

        //Sorting example, MongoRepository extends PagingAndSortingRepository
        System.out.println("\nFind all Departments, sort alphabetically by  name");
        departmentRepo.findAll(Sort.by(Sort.Direction.ASC, "name")).forEach(System.out::println);

        //Property Expression
        System.out.println("\nFind Department with the exact name 'Humanities' \n" +
                departmentRepo.findByName("Humanities"));

        //@Query with JSON query string that accepts regular expression as a parameter
        //{ 'name' : { $regex: ?0 } }
        //Any department name that ends in sciences where 's' is case insensitive
        System.out.println("\nFind all Departments with name ending in Sciences");
        departmentRepo.findNameByPattern(".[Ss]ciences").forEach(System.out::println);

        System.out.println("\nFind Departments chaired by Dean Jones");
        departmentRepo.findByChairId(deanJones.getId()).forEach(System.out::println);
    }

    // Helper methods
    private Staff createStaff(String firstName, String lastName) {
        return staffRepo.save(new Staff(new Person(firstName, lastName)));
    }

    private Department createDepartment(String departmentName, Staff departmentChair) {
        return departmentRepo.findByName(departmentName)
                .orElse(departmentRepo.save(new Department(departmentName, departmentChair)));
    }
}
