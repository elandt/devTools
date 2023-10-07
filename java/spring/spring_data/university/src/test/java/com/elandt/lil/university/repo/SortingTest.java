package com.elandt.lil.university.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.elandt.lil.university.business.UniversityService;

@SpringBootTest
public class SortingTest {
    @Autowired
    private StaffRepo staffRepo;
    @Autowired
    private UniversityService universityService;

    @Test
    public void testEquivalentQueries() {
        UniversityFactory.fillUniversity(universityService);
        assertEquals(
                staffRepo.findAllByOrderByMemberLastNameAscMemberFirstNameAsc(),
                staffRepo.findAll(Sort.by("member.lastName", "member.firstName")),
                "findAll with Sort result does NOT match findAllByOrderByMemberLastNameAscMemberFirstNameAsc result"
        );
    }
}
