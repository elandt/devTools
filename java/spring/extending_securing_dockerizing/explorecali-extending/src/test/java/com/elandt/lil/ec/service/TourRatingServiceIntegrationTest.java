package com.elandt.lil.ec.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.repo.TourRatingRepository;

@SpringBootTest
@Transactional
public class TourRatingServiceIntegrationTest {
    private static final int CUSTOMER_ID = 456;
    private static final int TOUR_ID = 1;
    private static final int NOT_A_TOUR_ID = 123;

    // Service under test
    @Autowired
    private TourRatingService tourRatingService;

    // Brought in rather than changing the visibility of private methods in the service purely for enabling test verification
    @Autowired
    private TourRatingRepository tourRatingRepository;

    @Test
    void testDelete() {
        List<TourRating> tourRatings = tourRatingService.lookupAll();
        tourRatingService.delete(tourRatings.get(0).getTour().getId(), tourRatings.get(0).getCustomerId());

        assertEquals(tourRatings.size() - 1, tourRatingService.lookupAll().size());
    }

    @Test
    void testDeleteWithInvalidTourId() {
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            tourRatingService.delete(NOT_A_TOUR_ID, CUSTOMER_ID);
        });

        assertEquals("Tour rating not found for Tour: "
        + NOT_A_TOUR_ID + " and Customer: " + CUSTOMER_ID, exception.getMessage());
    }

    @Test
    void testCreateNew() {
        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, 2, "It was alright");

        TourRating newTourRating = tourRatingRepository.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID).get();
        assertEquals(TOUR_ID, newTourRating.getTour().getId());
        assertEquals(CUSTOMER_ID, newTourRating.getCustomerId());
        assertEquals(2, newTourRating.getScore());
        assertEquals("It was alright", newTourRating.getComment());
    }

    @Test
    void testCreateNewWithDuplicateCustomerId() {
        // First execution will be fine
        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, 5, "It was Great");
        // Second for the same customer will error
        Throwable exception = assertThrows(DataIntegrityViolationException.class, () -> {
            tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, 2, "It was alright");
        });

        assertTrue(exception.getMessage().contains("PUBLIC.MYCONSTRAINT_INDEX_E ON PUBLIC.TOUR_RATING(TOUR_ID NULLS FIRST, CUSTOMER_ID NULLS FIRST)"));
    }

    @Test
    void testCreateNewWithInvalidTourId() {
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            tourRatingService.createNew(NOT_A_TOUR_ID, CUSTOMER_ID, 2, "It was alright");
        });

        assertEquals("Tour does not exist " + NOT_A_TOUR_ID, exception.getMessage());
    }

    @Test
    void testRateMany() {
        int ratings = tourRatingService.lookupAll().size();
        Integer[] customers = {100, 101, 102};
        tourRatingService.rateMany(TOUR_ID, 3, customers);
        assertEquals(ratings + customers.length, tourRatingService.lookupAll().size());
    }

    @Test
    void testRateManyProveRollback() {
        Integer[] customers = {100, 101, 102};
        // First execution will be fine
        tourRatingService.rateMany(TOUR_ID, 3, customers);
        // Second execution creates duplicates
        Throwable exception = assertThrows(DataIntegrityViolationException.class, () -> {
            tourRatingService.rateMany(TOUR_ID, 3, customers);
        });

        assertTrue(exception.getMessage().contains("PUBLIC.MYCONSTRAINT_INDEX_E ON PUBLIC.TOUR_RATING(TOUR_ID NULLS FIRST, CUSTOMER_ID NULLS FIRST)"));
    }

    @Test
    void testUpdate() {
        // Create new tourRating and verify results - could technically call testCreateNew() here, but that feels strange
        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, 5, "Great");
        TourRating newTourRating = tourRatingRepository.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID).get();
        assertEquals(TOUR_ID, newTourRating.getTour().getId());
        assertEquals(CUSTOMER_ID, newTourRating.getCustomerId());
        assertEquals(5, newTourRating.getScore());
        assertEquals("Great", newTourRating.getComment());

        // Update tour rating
        TourRating updatedTourRating = tourRatingService.update(TOUR_ID, CUSTOMER_ID, 1, "Terrible");

        assertEquals(TOUR_ID, updatedTourRating.getTour().getId());
        assertEquals(CUSTOMER_ID, updatedTourRating.getCustomerId());
        assertEquals(1, updatedTourRating.getScore());
        assertEquals("Terrible", updatedTourRating.getComment());
    }

    @Test
    void testUpdateForNonexistentTourRating() {
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            tourRatingService.update(TOUR_ID, 1, 1, "Terrible");
        });

        assertEquals("Tour rating not found for Tour: "
        + TOUR_ID + " and Customer: 1", exception.getMessage());
    }

    @Test
    void testUpdateSome() {
        // Create new tourRating and verify results - could technically call testCreateNew() here, but that feels strange
        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, 5, "Great");
        TourRating newTourRating = tourRatingRepository.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID).get();
        assertEquals(TOUR_ID, newTourRating.getTour().getId());
        assertEquals(CUSTOMER_ID, newTourRating.getCustomerId());
        assertEquals(5, newTourRating.getScore());
        assertEquals("Great", newTourRating.getComment());


        // Update tour rating
        TourRating updatedTourRating = tourRatingService.updateSome(TOUR_ID, CUSTOMER_ID, 1, "Terrible");

        assertEquals(TOUR_ID, updatedTourRating.getTour().getId());
        assertEquals(CUSTOMER_ID, updatedTourRating.getCustomerId());
        assertEquals(1, updatedTourRating.getScore());
        assertEquals("Terrible", updatedTourRating.getComment());
    }

    @Test
    void testUpdateSomeForNonexistentTourRating() {
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            tourRatingService.updateSome(TOUR_ID, 1, 1, "Terrible");
        });

        assertEquals("Tour rating not found for Tour: "
        + TOUR_ID + " and Customer: 1", exception.getMessage());
    }

    @Test
    void testGetAverage() {
        assertEquals(5.0, tourRatingService.getAverage(TOUR_ID));
    }

    @Test
    void testGetAverageInvalidTourId() {
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            tourRatingService.getAverage(NOT_A_TOUR_ID);
        });

        assertEquals("Tour does not exist " + NOT_A_TOUR_ID, exception.getMessage());
    }
}
