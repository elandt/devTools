package com.elandt.lil.ec.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.elandt.lil.ec.domain.TourRating;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends JpaRepository<TourRating, Integer> {

    /**
     * Lookup all the TourRatings for a tour.
     *
     * @param tourId - the tour identifier
     * @return a List of any found TourRatings
     */
    List<TourRating> findByTourId(Integer tourId);

    /**
     * Lookup all the TourRatings for a tour.
     *
     * @param tourId - the tour identifier
     * @param pageable - the page of TourRatings to return
     * @return
     */
    Page<TourRating> findByTourId(Integer tourId, Pageable pageable);

    /**
     * Lookup a TourRating by the Tour ID and Customer ID
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @return Optional of found TourRatings
     */
    Optional<TourRating> findByTourIdAndCustomerId(Integer tourId, Integer customerId);
}
