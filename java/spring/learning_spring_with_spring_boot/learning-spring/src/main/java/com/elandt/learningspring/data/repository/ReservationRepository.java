package com.elandt.learningspring.data.repository;

import java.sql.Date;

import com.elandt.learningspring.data.entity.Reservation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    // Spring with Aspect the impl of this at runtime
    Iterable<Reservation> findReservationByReservationDate(Date date);
}
