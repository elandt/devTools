package com.elandt.lil.ec.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TourPackageTest {

    private TourPackage tourPackage1;
    private TourPackage tourPackage2;

    @BeforeEach
    public void setup() {
        tourPackage1 = new TourPackage("CC", "name");
        tourPackage2 = new TourPackage("CC", "name");
    }

    // Typically don't do this as it's pretty low value to test constructors and accessors. It's here purely as an example.
    @Test
    void testConstructorsAndGetters() {
        TourPackage tourPackage = new TourPackage("CC", "name");
        assertEquals("CC", tourPackage.getCode());
        assertEquals("name", tourPackage.getName());
    }

    @Test
    void testEquals() {
        assertEquals(tourPackage1, tourPackage2);
    }

    @Test
    void testHashCode() {
        assertEquals(tourPackage1.hashCode(), tourPackage2.hashCode());
    }

}
