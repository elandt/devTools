package com.elandt.lil.ec.web;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.repo.TourRatingRepository;
import com.elandt.lil.ec.repo.TourRepository;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    protected TourRatingController() {
        // Default constructor - protected to force use of contructor below
    }

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating) {
        verifyTour(tourId);
        // Need to create a 'new' TourRating so that the tourId is associated to the TourRating
        tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(), tourRating.getScore(), tourRating.getComment()));
    }

    /**
     * Lookup all the Ratings for a tour with paging.
     *
     * @param tourId tour identifier
     * @param pageable the desired page of Tour Ratings
     * @return The given page of Tour Ratings as RatingDto's
     */
    @GetMapping
    public Page<TourRating> getAllRatingsForTour(@PathVariable(value = "tourId") String tourId, Pageable pageable) {
        // Not verifying that the tour exists is fine because it simply returns no results.
        // I've included the call to verifyTour() because to reduce ambiguity
        // - no results could mean there are no ratings for a tour, or the tourId is invalid
        verifyTour(tourId);
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    /**
     * Calculate the average Score of a Tour.
     *
     * @param tourId tour identifier
     * @return Tuple of "average" and the average value.
     */
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings.")));
    }

    /**
     * Update both score and comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param tourRating rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PutMapping
    public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }

    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param tourRating rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getScore() != null) {
            rating.setScore(tourRating.getScore());
        }
        if (tourRating.getComment() != null) {
            rating.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(rating);
    }

    /**
     * Delete a tour rating made by a customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     */
    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId, @PathVariable(value = "customerId") int customerId) {
        tourRatingRepository.delete(verifyTourRating(tourId, customerId));
    }

    /**
     * Verify and return the TourRating for a particular tourId and Customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() ->
                new NoSuchElementException("Tour rating not found for Tour: "
                + tourId + " and Customer: " + customerId));
    }

    /**
     * Verify and return the Tour for a given {@code tourId}.
     *
     * @param tourId tour identifier
     * @throws NoSuchElementException if no Tour found.
     */
    private void verifyTour(String tourId) throws NoSuchElementException {
         if (!tourRepository.existsById(tourId)) {
            throw new NoSuchElementException("Tour does not exist " + tourId);
         }
    }

    /**
     * Exception handler for NoSuchElementExceptions thrown in this Controller.
     *
     * @param e exception
     * @return Error message String
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException e) {
        return e.getMessage();
    }
}
