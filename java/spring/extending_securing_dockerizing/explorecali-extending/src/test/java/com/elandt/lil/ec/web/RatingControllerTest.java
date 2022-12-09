package com.elandt.lil.ec.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elandt.lil.ec.domain.Tour;
import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.service.TourRatingService;
import com.elandt.lil.ec.web.dto.RatingDto;

// Using @SpringBootTest starts the actual app
// Using RANDOM_PORT prevents port collision when app is started for testing
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RatingControllerTest {

    private static final String RATINGS_URL = "/ratings";

    // These Tour and Rating IDs do not already exist in the database via the data.sql script
    private static final int TOUR_ID = 999;
    private static final int RATING_ID = 555;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";

    @MockBean
    private TourRatingService tourRatingServiceMock;

    @Mock
    private TourRating tourRatingMock;

    @Mock
    private Tour tourMock;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        when(tourRatingMock.getTour()).thenReturn(tourMock);
        when(tourMock.getId()).thenReturn(TOUR_ID);
        when(tourRatingMock.getComment()).thenReturn(COMMENT);
        when(tourRatingMock.getScore()).thenReturn(SCORE);
        when(tourRatingMock.getCustomerId()).thenReturn(CUSTOMER_ID);
    }

    /**
     * Test the GET /ratings endpoint
     */
    @Test
    void testGetAll() {
        when(tourRatingServiceMock.lookupAll()).thenReturn(Arrays.asList(tourRatingMock, tourRatingMock, tourRatingMock));

        ResponseEntity<List> response = restTemplate.getForEntity(RATINGS_URL, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    /**
     * Test the GET /ratings/{id} endpoint with a valid ID
     */
    @Test
    void testGetRating() {
        when(tourRatingServiceMock.lookupRatingById(RATING_ID)).thenReturn(Optional.of(tourRatingMock));

        ResponseEntity<RatingDto> response = restTemplate.getForEntity(RATINGS_URL + "/" + RATING_ID, RatingDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(CUSTOMER_ID, response.getBody().getCustomerId());
        assertEquals(COMMENT, response.getBody().getComment());
        assertEquals(SCORE, response.getBody().getScore());
    }

    /**
     * Test the GET /ratings/{id} endpoint with an invalid ID
     */
    @Test
    void testReturn404() {
        when(tourRatingServiceMock.lookupRatingById(RATING_ID)).thenReturn(Optional.empty());

        ResponseEntity<String> response = restTemplate.getForEntity(RATINGS_URL + "/" + RATING_ID, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Rating " + RATING_ID + " not found.", response.getBody());
    }
}
