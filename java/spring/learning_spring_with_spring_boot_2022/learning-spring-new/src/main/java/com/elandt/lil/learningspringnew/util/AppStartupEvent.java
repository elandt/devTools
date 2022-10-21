package com.elandt.lil.learningspringnew.util;

import java.util.Date;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import com.elandt.lil.learningspringnew.business.dto.RoomReservation;
import com.elandt.lil.learningspringnew.business.service.ReservationService;

public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final ReservationService reservationService;
    private final DateUtils dateUtils;

    public AppStartupEvent(ReservationService reservationService, DateUtils dateUtils) {
        this.reservationService = reservationService;
        this.dateUtils = dateUtils;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Date date = this.dateUtils.createDateFromDateString("2022-01-01");
        List<RoomReservation> resevations = this.reservationService.getRoomReservationsForDate(date);
        resevations.forEach(System.out::println);
    }    
}
