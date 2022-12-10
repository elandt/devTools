package com.elandt.lil.ec.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TourTest {

    private TourPackage tourPackage = new TourPackage("CC", "name");

    private Tour tour1;
    private Tour tour2;

    @BeforeEach
    public void setup() {
        tour1 = new Tour("title", "description", "blurb", 5, "5 days", "bullet",
                "keywords", tourPackage, Difficulty.Difficult, Region.Central_Coast);
        tour2 = new Tour("title", "description", "blurb", 5, "5 days", "bullet",
                "keywords", tourPackage, Difficulty.Difficult, Region.Central_Coast);
    }

    @Test
    void testEquals() {
        assertEquals(tour1, tour2);
    }

    @Test
    void testHashCode() {
        assertEquals(tour1.hashCode(), tour2.hashCode());
    }

}
