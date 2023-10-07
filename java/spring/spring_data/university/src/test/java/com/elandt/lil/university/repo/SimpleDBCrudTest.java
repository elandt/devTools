package com.elandt.lil.university.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Staff;

@SpringBootTest
public class SimpleDBCrudTest {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StaffRepo staffRepo;

    private List<Staff> allStaff;
    private Optional<Staff> oneStaff;
    private int id;

    @Test
    public void testStaffCrud() {
        // Test Create
        UniversityFactory.fillUniversity(universityService);
        // Test FindAll
        List<Staff> allStaff = universityService.findAllStaff();
        int totalStaff = allStaff.size();
        allStaff.stream().forEach(System.out::println);
        assertEquals(11,  allStaff.size());

        // Test Find by Id
        Staff deanThomas = allStaff.get(0);
        System.out.println(deanThomas);
        assertEquals(deanThomas, staffRepo.findById(deanThomas.getId()).get());

        // Test Update, Change first Name to Patrick
        deanThomas.getMember().setFirstName("Patrick");
        staffRepo.save(deanThomas);

        assertEquals("Patrick",
                staffRepo.findById(deanThomas.getId()).get().getMember().getFirstName());

        staffRepo.delete(deanThomas);
        allStaff = staffRepo.findAll();
        assertEquals(totalStaff -1, allStaff.size());
        allStaff.stream().forEach(System.out::println);
    }
}
