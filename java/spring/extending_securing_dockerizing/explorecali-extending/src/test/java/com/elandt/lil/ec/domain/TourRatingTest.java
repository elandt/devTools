package com.elandt.lil.ec.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TourRatingTest {

    private Tour tour = new Tour("title","description","blurb", 50, "1 day", "bullet",
            "keywords",new TourPackage("CC","name"), Difficulty.Difficult, Region.Central_Coast);

    private TourRating rating1;
    private TourRating rating2;

    @BeforeEach
    public void setup() {
        rating1 = new TourRating(tour, 1, 1, "comment");
        rating2 = new TourRating(tour, 1, 1, "comment");
    }

    @Test
    public void testEquals() {
        assertEquals(rating1, rating2);
    }

    @Test
    public void testHashCode() {
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }
}
