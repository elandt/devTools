package com.elandt.lil.university.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.elandt.lil.university.business.UniversityService;
import com.elandt.lil.university.domain.Staff;

@SpringBootTest
public class PagingTest {

    @Autowired
    private StaffRepo staffRepo;
    @Autowired
    private UniversityService universityService;

    @Test
    void findPage() {
        UniversityFactory.fillUniversity(universityService);
        List<Staff> allStaff = universityService.findAllStaff();
        Staff firstStaff = allStaff.get(0);
        Page<Staff> staffSpringDataPage = staffRepo.findAll(PageRequest.of(0, 5, Sort.by(Sort.Order.asc("member.lastName"))));
        List<Staff> staffPage = staffSpringDataPage.getContent();
        assertTrue(staffPage.get(0).getMember().getLastName().compareTo(staffPage.get(1).getMember().getLastName()) < 0);
        assertTrue(staffPage.get(1).getMember().getLastName().compareTo(staffPage.get(2).getMember().getLastName()) < 0);
        assertTrue(staffPage.get(2).getMember().getLastName().compareTo(staffPage.get(3).getMember().getLastName()) < 0);
        assertTrue(staffPage.get(3).getMember().getLastName().compareTo(staffPage.get(4).getMember().getLastName()) < 0);
    }
}
