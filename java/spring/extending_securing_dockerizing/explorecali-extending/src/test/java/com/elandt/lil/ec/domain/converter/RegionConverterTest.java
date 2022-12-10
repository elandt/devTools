package com.elandt.lil.ec.domain.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.elandt.lil.ec.domain.Region;

public class RegionConverterTest {

    private RegionConverter regionConverter = new RegionConverter();

    @Test
    public void testConvetToDatabaseColumn() {
        assertEquals(Region.Central_Coast.getLabel(), regionConverter.convertToDatabaseColumn(Region.Central_Coast));
    }

    @Test
    public void testConvertToEntityAttribute() {
        assertEquals(Region.Central_Coast, regionConverter.convertToEntityAttribute(Region.Central_Coast.getLabel()));
    }
}
