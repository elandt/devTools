package com.elandt.lil.ec.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.elandt.lil.ec.domain.Tour;

public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {
    // By extending CrudRepository a number of db operations are provided out-of-the-box

    Page<Tour> findByTourPackageCode(@Param("code") String code, Pageable pageable);

    @Override
    void delete(Tour entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> entities);

    @Override
    @RestResource(exported = false)
    void deleteAllById(Iterable<? extends Integer> ids);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer id);

    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Iterable<S> saveAll(Iterable<S> entities);
}
