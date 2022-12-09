package com.elandt.lil.ec.web;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.ec.service.TourRatingService;
import com.elandt.lil.ec.web.dto.RatingDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/ratings")
@Tag(name = "Rating", description = "The Rating API")
public class RatingController {
    private static final Logger LOGGER = LogManager.getLogger(RatingController.class);
    private final TourRatingService tourRatingService;

    public RatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    /**
     * Find all the Tour Ratings and return them as RatingDtos
     *
     * @return a List of all RatingDtos
     */
    @GetMapping
    @Operation(summary = "Find all ratings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<RatingDto> getAll() {
        LOGGER.info("GET /ratings");
        return tourRatingService.lookupAll().stream()
                .map(tourRating -> new RatingDto(tourRating))
                .collect(Collectors.toList());
    }

    /**
     * Find an individual Tour Rating and return it as a RatingDto
     *
     * @param id the TourRating id
     * @return RatingDto representation of the desired TourRating
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Find ratings by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
                            @ApiResponse(responseCode = "404", description = "Rating not found")})
    public RatingDto getRating(@PathVariable(value = "id") Integer id) {
        LOGGER.info("GET /ratings/{id}", id);
        return tourRatingService.lookupRatingById(id)
                .map(tourRating -> new RatingDto(tourRating))
                .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found."));
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
        LOGGER.error("Unable to complete transaction.", e);
        return e.getMessage();
    }
}
