package com.elandt.lil.ec.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.elandt.lil.ec.domain.Tour;
import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.repo.TourRatingRepository;
import com.elandt.lil.ec.repo.TourRepository;

// Effectively replaces what would be @RunWith(MockitoJUnitRunner.class) in JUnit 4
// Using @SpringBootTest here also works, but introduces more than is necessary - including a Spring App Context
@ExtendWith(MockitoExtension.class)
public class TourRatingServiceTest {

    private static final int CUSTOMER_ID = 123;
    private static final int TOUR_ID = 1;
    private static final int TOUR_RATING_ID = 100;

    @Mock
    private TourRepository  tourRepositoryMock;

    @Mock
    private TourRatingRepository tourRatingRepositoryMock;

    // Either use the @InjectMocks annotation or have a setup() method annotated with @BeforeEach that
    // instaniates the service using the mocks.
    // @InjectMocks // Autowire TourRatingService(tourRatingRepositoryMock, tourRepositoryMock)
    private TourRatingService tourRatingService;

    @Mock
    private Tour tourMock;

    @Mock
    private TourRating tourRatingMock;

    @BeforeEach
    public void setup() {
        // This replaces using @InjectMocks on the tourRatingService declaration for our use case
        tourRatingService = new TourRatingService(tourRatingRepositoryMock, tourRepositoryMock);

        // Stubbing is set up only in the specific tests in which it is used
        // This is because UnnecessaryStubbingException occurs if there are stubs
        // defined for tests that don't use them
    }

    // Testing return values of the service

    @Test
    void testLookupRatingById() {
        when(tourRatingRepositoryMock.findById(TOUR_RATING_ID)).thenReturn(Optional.of(tourRatingMock));

        assertEquals(tourRatingMock, tourRatingService.lookupRatingById(TOUR_RATING_ID).get());
    }

    @Test
    void testLookupAll() {
        when(tourRatingRepositoryMock.findAll()).thenReturn(Arrays.asList(tourRatingMock));

        assertEquals(tourRatingMock, tourRatingService.lookupAll().get(0));
    }

    @Test
    void testGetAverage() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        when(tourRatingRepositoryMock.findByTourId(TOUR_ID)).thenReturn(Arrays.asList(tourRatingMock));
        when(tourRatingMock.getScore()).thenReturn(5);

        assertEquals(5,  tourRatingService.getAverage(TOUR_ID));
    }

    @Test
    void testLookupRatings() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        when(tourMock.getId()).thenReturn(TOUR_ID);
        // These mocks are only needed for this test
        Pageable pageable = mock(Pageable.class);
        Page<TourRating> page = mock(Page.class);
        when(tourRatingRepositoryMock.findByTourId(TOUR_ID, pageable)).thenReturn(page);

        assertEquals(page, tourRatingService.lookupRatings(TOUR_ID, pageable));
    }

    // Testing the invocation of dependencies (without testing the dependency because it's mocked)

    @Test
    void testDelete() {
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID)).thenReturn(Optional.of(tourRatingMock));
        tourRatingService.delete(TOUR_ID, CUSTOMER_ID);

        verify(tourRatingRepositoryMock).delete(any(TourRating.class));
    }

    @Test
    void testRateMany() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        tourRatingService.rateMany(TOUR_ID, 5, new Integer[] {CUSTOMER_ID, CUSTOMER_ID + 1});

        verify(tourRatingRepositoryMock, times(2)).save(any(TourRating.class));
    }

    @Test
    void testUpdate() {
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID)).thenReturn(Optional.of(tourRatingMock));
        tourRatingService.update(TOUR_ID, CUSTOMER_ID, 5, "great");

        verify(tourRatingRepositoryMock).save(any(TourRating.class));

        verify(tourRatingMock).setComment("great");
        verify(tourRatingMock).setScore(5);
    }

    @Test
    void testUpdateSome() {
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID)).thenReturn(Optional.of(tourRatingMock));
        tourRatingService.updateSome(TOUR_ID, CUSTOMER_ID, 1, "awful");

        verify(tourRatingRepositoryMock).save(any(TourRating.class));

        verify(tourRatingMock).setComment("awful");
        verify(tourRatingMock).setScore(1);
    }

    // Testing invocation of dependencies, capturing parameter values, and verifying they're as expected

    /**
     * Verifies invocation of dependencies, captures the parameter values, and verifies the parameters
     */
    @Test
    void testCreateNew() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        ArgumentCaptor<TourRating> tourRatingCaptor = ArgumentCaptor.forClass(TourRating.class);

        tourRatingService.createNew(TOUR_ID, CUSTOMER_ID, 2, "ok");

        verify(tourRatingRepositoryMock).save(tourRatingCaptor.capture());

        assertEquals(tourMock, tourRatingCaptor.getValue().getTour());
        assertEquals(CUSTOMER_ID, tourRatingCaptor.getValue().getCustomerId());
        assertEquals(2, tourRatingCaptor.getValue().getScore());
        assertEquals("ok", tourRatingCaptor.getValue().getComment());
    }
}
