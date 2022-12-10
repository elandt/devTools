package com.elandt.lil.ec.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RegionTest {

    @Test
    public void testFindByLabel() {
        assertEquals(Region.Central_Coast, Region.findByLabel("Central Coast"));
    }
}
