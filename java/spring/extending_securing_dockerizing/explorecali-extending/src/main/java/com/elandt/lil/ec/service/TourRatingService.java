package com.elandt.lil.ec.service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elandt.lil.ec.domain.Tour;
import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.repo.TourRatingRepository;
import com.elandt.lil.ec.repo.TourRepository;

@Service
// Will rollback a transaction if it fails - i.e. the database will be in the same state it was prior to the failed method call
@Transactional
public class TourRatingService {

    private static final Logger LOGGER = LogManager.getLogger(TourRatingService.class);

    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;

    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    /**
     * Creat a new Tour Rating in the database
     *
     * @param tourId id of the tour being rated
     * @param customerId id of the customer submitting the rating
     * @param score Integer score (1-5)
     * @param comment Additonal comment
     * @throws NoSuchElementException if no Tour found for the given id
     */
    public void createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId, score, comment));
    }

    /**
     * Create TourRatings with the same score for multiple customers
     *
     * @param tourId id of tour being rated
     * @param score Integer score (1-5) to apply to all ratings
     * @param customers Array of customer ids that are submitting ratings
     */
    public void rateMany(int tourId, Integer score, Integer[] customers) {
        LOGGER.info("Rate tour {} by customers: {}.", tourId, Arrays.asList(customers));
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException("Tour does not exist."));
        for (Integer customer : customers) {
            LOGGER.debug("Attempt to create Tour Rating for customer: {}", customer);
            tourRatingRepository.save(new TourRating(tour, customer, score));
        }
    }

    public Optional<TourRating> lookupRatingById(int id) {
        return tourRatingRepository.findById(id);
    }

    public List<TourRating> lookupAll() {
        LOGGER.info("Lookup all Ratings.");
        return tourRatingRepository.findAll();
    }

    /**
     * Get a page of tour ratings for a tour
     *
     * @param tourId id of tour to return ratings for
     * @param pageable page parameters to determine which elements to fetch
     * @return Page of TourRatings
     * @throws NoSuchElementException if not Tour found for the given id
     */
    public Page<TourRating> lookupRatings(int tourId, Pageable pageable) throws NoSuchElementException {
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId(), pageable);
    }

    /**
     * Update all of the elements of a Tour Rating.
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @param score score of the tour rating
     * @param comment additional comment
     * @return Tour Rating Domain Object
     * @throws NoSuchElementException if no Tour Rating found.
     */
    public TourRating update(int tourId, Integer customerId, Integer score, String comment)  throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return tourRatingRepository.save(rating);
    }

    /**
     * Update some of the elements of a Tour Rating.
     *
     * @param tourId tour identifier
     * @param score score of the tour rating
     * @param comment additional comment
     * @return Tour Rating Domain Object
     * @throws NoSuchElementException if no Tour Rating found.
     */
    public TourRating updateSome(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment != null) {
            rating.setComment(comment);
        }
        return tourRatingRepository.save(rating);
    }

    /**
     * Delete a tour rating made by a customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @throws NoSuchElementException if no Tour Rating found.
     */
    public void delete(int tourId, Integer customerId) throws NoSuchElementException {
        tourRatingRepository.delete(verifyTourRating(tourId, customerId));
    }

    /**
     * Calculate the average Score of a Tour.
     *
     * @param tourId tour identifier
     * @return the average rating for the given tour
     * @throws NoSuchElementException if no Tour found OR the given tour has no ratings.
     */
    public Double getAverage(int tourId) {
        verifyTour(tourId);
        LOGGER.info("Get average score of tour {}", tourId);
        return tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings."));
    }

    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId id of the desired tour
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId)
        );
    }

    /**
     * Verify and return the Tour for a given {@code tourId}.
     *
     * @param tourId tour identifier
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    private TourRating verifyTourRating(int tourId, Integer customerId) {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() ->
                new NoSuchElementException("Tour rating not found for Tour: "
                + tourId + " and Customer: " + customerId));
    }
}
