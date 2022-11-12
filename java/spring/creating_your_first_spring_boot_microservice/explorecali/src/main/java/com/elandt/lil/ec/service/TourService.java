package com.elandt.lil.ec.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.elandt.lil.ec.domain.Tour;
import com.elandt.lil.ec.domain.TourPackage;
import com.elandt.lil.ec.repo.TourPackageRepository;
import com.elandt.lil.ec.repo.TourRepository;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    /**
     * Create a new Tour Object and persist it to the database
     *
     * @param title title
     * @param tourPackageName name of tour packe to assocate Tour with
     * @param details extra details about the tour
     * @return Tour
     */
    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {

        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour Package does not exist: " + tourPackageName));

        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    /**
     * Calculate the number of Tours in the database
     *
     * @return the total number of tours
     */
    public long total() {
        return tourRepository.count();
    }

}
