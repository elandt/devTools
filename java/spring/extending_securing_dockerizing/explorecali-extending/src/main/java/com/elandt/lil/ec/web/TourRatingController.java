package com.elandt.lil.ec.web;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.ec.domain.TourRating;
import com.elandt.lil.ec.service.TourRatingService;
import com.elandt.lil.ec.web.dto.RatingDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
@Tag(name = "Tour Rating", description = "The Rating for a Tour API")
public class TourRatingController {

    private static final Logger LOGGER = LogManager.getLogger(TourRatingController.class);

    TourRatingService tourRatingService;

    protected TourRatingController() {
        // Default constructor - protected to force use of contructor below
    }

    @Autowired
    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CSR')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Tour Rating")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        LOGGER.info("POST /tours/{}/ratings", tourId);
        tourRatingService.createNew(tourId,
                ratingDto.getCustomerId(),
                ratingDto.getScore(),
                ratingDto.getComment());
    }

    @PostMapping(path = "/{score}")
    @PreAuthorize("hasRole('ROLE_CSR')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Give the same score to a Tour from multiple customers")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                      @PathVariable(value = "score") int score,
                                      @RequestParam(value = "customers") Integer[] customers) {
        LOGGER.info("POST /tour/{}/ratings/{}", tourId, score);
        tourRatingService.rateMany(tourId, score, customers);
    }

    /**
     * Lookup all the Ratings for a tour with paging.
     *
     * @param tourId tour identifier
     * @param pageable the desired page of Tour Ratings
     * @return The given page of Tour Ratings as RatingDto's
     */
    @GetMapping
    @Operation(summary = "Look up all ratings for a Tour")
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
        LOGGER.info("GET /tours/{}/ratings", tourId);
        Page<TourRating> ratings = tourRatingService.lookupRatings(tourId, pageable);
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
    @Operation(summary = "Get the average rating for a Tour")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        LOGGER.info("GET /tours/{}/ratings/average", tourId);
        return Map.of("average", tourRatingService.getAverage(tourId));
    }

    /**
     * Update both score and comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param ratingDto rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PutMapping
    @PreAuthorize("hasRole('ROLE_CSR')")
    @Operation(summary = "Modify all Tour Rating attributes")
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        LOGGER.info("PUT /tours/{}/ratings", tourId);
        return new RatingDto(tourRatingService.update(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment()));
    }

    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param ratingDto rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PatchMapping
    @PreAuthorize("hasRole('ROLE_CSR')")
    @Operation(summary = "Modify one or more Tour Rating attributes")
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        LOGGER.info("PATCH /tours/{}/ratings", tourId);
        return new RatingDto(tourRatingService.updateSome(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment()));
    }

    /**
     * Delete a tour rating made by a customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     */
    @DeleteMapping(path = "/{customerId}")
    @PreAuthorize("hasRole('ROLE_CSR')")
    @Operation(summary = "Delte a Customer's Rating of a Tour")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        LOGGER.info("DELETE /tours/{}/ratings/{}", tourId, customerId);
        tourRatingService.delete(tourId, customerId);
    }

    /**
     * Exception handler for NoSuchElementExceptions thrown in this Controller.
     *
     * @param e exception
     * @return Error message String
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return404(NoSuchElementException e) {
        LOGGER.error("Unable to complete transaction", e);
        return e.getMessage();
    }
}
