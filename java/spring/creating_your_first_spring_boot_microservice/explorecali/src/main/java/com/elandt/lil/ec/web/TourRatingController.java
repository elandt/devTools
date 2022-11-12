package com.elandt.lil.ec.web;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import com.elandt.lil.ec.domain.Tour;
import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.domain.TourRatingPk;
import com.elandt.lil.ec.repo.TourRatingRepository;
import com.elandt.lil.ec.repo.TourRepository;
import com.elandt.lil.ec.web.dto.RatingDto;

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
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(
                new TourRatingPk(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(),
                ratingDto.getComment()));
    }

    /**
     * Lookup all the Ratings for a tour.
     *
     * @param tourId tour identifier
     * @return All Tour Ratings as RatingDto's
     */
    // Can't have both this and the example with paging (line 80) enabled at the same time because they use the same path
    // @GetMapping
    // public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
    //     verifyTour(tourId);
    //     return tourRatingRepository.findByPkTourId(tourId).stream().map(RatingDto::new).collect(Collectors.toList());
    // }

    /**
     * Lookup all the Ratings for a tour with paging.
     *
     * @param tourId tour identifier
     * @param pageable the desired page of Tour Ratings
     * @return The given page of Tour Ratings as RatingDto's
     */
    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
        verifyTour(tourId);
        Page<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId, pageable);
        return new PageImpl<>(
            ratings.get().map(RatingDto::new).collect(Collectors.toList()),
            pageable,
            ratings.getTotalElements()
        );
    }

    /**
     * Calculate the average Score of a Tour.
     *
     * @param tourId tour identifier
     * @return Tuple of "average" and the average value.
     */
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByPkTourId(tourId).stream()
                .mapToInt(TourRating::getScore)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings.")));
    }

    /**
     * Update both score and comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param ratingDto rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
    }

    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param ratingDto rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        if (ratingDto.getScore() != null) {
            rating.setScore(ratingDto.getScore());
        }
        if (ratingDto.getComment() != null) {
            rating.setComment(ratingDto.getComment());
        }
        return new RatingDto(tourRatingRepository.save(rating));
    }

    /**
     * Delete a tour rating made by a customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     */
    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
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
    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId)
                .orElseThrow(() ->
                new NoSuchElementException("Tour rating not found for Tour: "
                + tourId + " and Customer: " + customerId));
    }

    /**
     * Verify and return the Tour for a given {@code tourId}.
     *
     * @param tourId tour identifier
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
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
