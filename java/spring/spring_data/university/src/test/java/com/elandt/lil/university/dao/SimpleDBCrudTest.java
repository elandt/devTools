package com.elandt.lil.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.elandt.lil.university.PersistenceJPAConfig;
import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Staff;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class })
public class SimpleDBCrudTest {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StaffDao staffDao;

    private List<Staff> allStaff;
    private Optional<Staff> oneStaff;
    private int id;

    @Test
    public void testStaffCrud() {
        // Test Create
        UniversityFactory.fillUniversity(universityService);
        //Test FindA ll
        List<Staff> allStaff = universityService.findAllStaff();
        int totalStaff = allStaff.size();
        allStaff.stream().forEach(System.out::println);
        assertEquals(11,  allStaff.size());

        // Test Find by Id
        Staff deanThomas = allStaff.get(0);
        System.out.println(deanThomas);
        assertEquals(deanThomas, staffDao.findById(deanThomas.getId()).get());

        // Test Update, Change first Name to Patrick
        deanThomas.getMember().setFirstName("Patrick");
        staffDao.save(deanThomas);

        assertEquals("Patrick",
                staffDao.findById(deanThomas.getId()).get().getMember().getFirstName());

        staffDao.delete(deanThomas);
        allStaff = staffDao.findAll();
        assertEquals(totalStaff -1, allStaff.size());
        allStaff.stream().forEach(System.out::println);
    }
}
