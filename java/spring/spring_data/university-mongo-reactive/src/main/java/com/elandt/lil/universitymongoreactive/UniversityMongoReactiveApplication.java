package com.elandt.lil.universitymongoreactive;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.universitymongoreactive.domain.Department;
import com.elandt.lil.universitymongoreactive.domain.Person;
import com.elandt.lil.universitymongoreactive.domain.Staff;
import com.elandt.lil.universitymongoreactive.repo.DepartmentRepo;
import com.elandt.lil.universitymongoreactive.repo.StaffRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Transactional
public class UniversityMongoReactiveApplication implements CommandLineRunner {

	@Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private StaffRepo staffRepo;

	public static void main(String[] args) {
		SpringApplication.run(UniversityMongoReactiveApplication.class, args);
	}

	@Override
    public void run(String... strings) throws Exception {
		// These two calls don't actually insert anything into the database
        Mono<Staff> deanJonesMono = staffRepo.save(new Staff( new Person("John", "Jones")));
        Mono<Staff> deanMartinMono = staffRepo.save(new Staff(new Person("John", "Martin")));
		// This counts the saved records in the database due to the use of `.block()`
        System.out.println("Staff count = " + staffRepo.count().block());
		// These two lines actually insert the records in the database
        Staff deanJones = deanJonesMono.block();
        Staff deanMartin = deanMartinMono.block();
		// Again, due to the use of `.block()` this call to `.count()` executes immediately - now their are records in the db from the previous two lines
        System.out.println("blocked(): Staff count = " + staffRepo.count().block());
        Flux<Department> departmentFlux = departmentRepo.saveAll(
                Arrays.asList(new Department("Humanities", deanJones),
                        new Department("Natural Sciences", deanMartin),
                        new Department("Social Sciences", deanJones)));
		// This triggers the insertion of the two departments from the `departmentFlux`
        departmentFlux.subscribe();
    }

    @GetMapping("/staff")
    public Flux<Staff> getAllStaff() {
        return staffRepo.findAll();
    }

    @GetMapping("/staff/{id}")
    public Mono<Staff> getStaffById(@PathVariable("id") String id) {
        return staffRepo.findById(id);
    }

    @GetMapping("/departments")
    public Flux<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @GetMapping("/departments/{id}")
    public Mono<Department> getDepartmentById(@PathVariable("id") String id) {
        return departmentRepo.findById(id);
    }

    @GetMapping("/staff/search/member/{lastName}")
    public Flux<Staff> getStaffByLastName(@PathVariable("lastName") String lastName) {
        return staffRepo.findByMemberLastName(lastName);
    }
}
