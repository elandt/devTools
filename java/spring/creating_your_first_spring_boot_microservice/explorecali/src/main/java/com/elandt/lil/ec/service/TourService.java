package com.elandt.lil.ec.service;

import org.springframework.stereotype.Service;

import com.elandt.lil.ec.domain.Difficulty;
import com.elandt.lil.ec.domain.Region;
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
     * @param description short description
     * @param blurb long description
     * @param price price
     * @param duration duration
     * @param bullets comma-separated bullets
     * @param keywords keywords
     * @param tourPackageName name of tour packe to assocate Tour with
     * @param difficulty difficulty
     * @param region region
     * @return Tour entity
     */
    public Tour createTour(String title, String description, String blurb, Integer price,
                            String duration, String bullets, String keywords, String tourPackageName,
                            Difficulty difficulty, Region region) {

        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour Package does not exist: " + tourPackageName));

        return tourRepository.save(new Tour(title, description, blurb,
                price, duration, bullets, keywords, tourPackage, difficulty, region));
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
