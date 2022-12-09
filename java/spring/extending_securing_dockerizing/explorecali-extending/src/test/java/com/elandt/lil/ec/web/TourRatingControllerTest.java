package com.elandt.lil.ec.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.elandt.lil.ec.domain.Tour;
import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.service.TourRatingService;
import com.elandt.lil.ec.web.dto.RatingDto;
import com.elandt.lil.ec.web.helper.JwtRequestHelper;

// Using @SpringBootTest starts the actual app
// Using RANDOM_PORT prevents port collision when app is started for testing
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TourRatingControllerTest {

    //These Tour and rating id's do not already exist in the db
    private static final int TOUR_ID = 999;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";
    private static final String TOUR_RATINGS_URL = "/tours/" + TOUR_ID + "/ratings";

    @MockBean
    private TourRatingService tourRatingServiceMock;

    @Mock
    private TourRating tourRatingMock;

    @Mock
    private Tour tourMock;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtRequestHelper jwtRequestHelper;

    @BeforeEach
    public void setup() {
        when(tourRatingMock.getTour()).thenReturn(tourMock);
        when(tourMock.getId()).thenReturn(TOUR_ID);
        when(tourRatingMock.getComment()).thenReturn(COMMENT);
        when(tourRatingMock.getScore()).thenReturn(SCORE);
        when(tourRatingMock.getCustomerId()).thenReturn(CUSTOMER_ID);
    }

    /**
     * Test the POST /tours/{tourId}/ratings/{score}?customers={ids..} endpoint
     */
    @Test
    void testCreateManyTourRatings() {
        ResponseEntity<Void> response = restTemplate.exchange(
                TOUR_RATINGS_URL + "/" + SCORE + "?customers=" + CUSTOMER_ID + "," + (CUSTOMER_ID + 1) + "," + (CUSTOMER_ID + 2),
                HttpMethod.POST,
                new HttpEntity<>(null, jwtRequestHelper.withRole("ROLE_CSR")),
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(tourRatingServiceMock).rateMany(TOUR_ID, SCORE, new Integer[] {CUSTOMER_ID, CUSTOMER_ID + 1, CUSTOMER_ID + 2});
    }

    /**
     * Test the POST /tours/{tourId}/ratings endpoint
     */
    @Test
    void testCreateTourRating() {
        ResponseEntity<Void> response = restTemplate.exchange(TOUR_RATINGS_URL,
                HttpMethod.POST,
                new HttpEntity<>(new RatingDto(tourRatingMock), jwtRequestHelper.withRole("ROLE_CSR")),
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(tourRatingServiceMock).createNew(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);
    }

    /**
     * Test the DELETE /tours/{tourId}/ratings endpoint
     */
    @Test
    void testDelete() {
        restTemplate.exchange(TOUR_RATINGS_URL + "/" + CUSTOMER_ID,
                HttpMethod.DELETE,
                new HttpEntity<>(jwtRequestHelper.withRole("ROLE_CSR")),
                Void.class);

        verify(tourRatingServiceMock).delete(TOUR_ID, CUSTOMER_ID);
    }

    /**
     * Test the GET /tours/{tourId}/ratings endpoint
     */
    @Test
    void testGetAllRatingsForTour() {
        when(tourRatingServiceMock.lookupRatings(anyInt(), any(Pageable.class)))
                .thenReturn(new PageImpl(Arrays.asList(tourRatingMock), PageRequest.of(0, 10), 1));

        ResponseEntity<String> response = restTemplate.getForEntity(TOUR_RATINGS_URL, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tourRatingServiceMock).lookupRatings(anyInt(), any(Pageable.class));
    }

    /**
     * Test the GET /tours/{tourId}/ratings/average endpoint
     */
    @Test
    void testGetAverage() {
        when(tourRatingServiceMock.getAverage(TOUR_ID)).thenReturn(3.2);

        ResponseEntity<String> response = restTemplate.getForEntity(TOUR_RATINGS_URL + "/average", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"average\":3.2}", response.getBody());
    }

    /**
     * Test the PATCH /tours/{tourId}/ratings endpoint
     *
     * Requires httpclient dependency from org.apache.httpcomponents
     */
    @Test
    void testUpdateWithPatch() {
        when(tourRatingServiceMock.updateSome(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT)).thenReturn(tourRatingMock);
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        restTemplate.exchange(TOUR_RATINGS_URL,
                HttpMethod.PATCH,
                new HttpEntity<>(new RatingDto(tourRatingMock), jwtRequestHelper.withRole("ROLE_CSR")),
                RatingDto.class);

        verify(tourRatingServiceMock).updateSome(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);
    }

    /**
     * Test the PUT /tours/{tourId}/ratings endpoint
     */
    @Test
    void testUpdateWithPut() {
        when(tourRatingServiceMock.update(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT)).thenReturn(tourRatingMock);

        restTemplate.exchange(TOUR_RATINGS_URL,
                HttpMethod.PUT,
                new HttpEntity<>(new RatingDto(tourRatingMock), jwtRequestHelper.withRole("ROLE_CSR")),
                Void.class);

        verify(tourRatingServiceMock).update(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);
    }

    /**
     * Test the GET /tours/{tourId}/ratings/average endpoint with an invalid Tour ID
     */
    @Test
    void testReturn404() {
        when(tourRatingServiceMock.getAverage(TOUR_ID)).thenThrow(NoSuchElementException.class);

        ResponseEntity<String> response = restTemplate.getForEntity(TOUR_RATINGS_URL + "/average", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
