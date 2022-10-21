package com.elandt.lil.learningspringnew.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elandt.lil.learningspringnew.business.dto.RoomReservation;
import com.elandt.lil.learningspringnew.business.service.ReservationService;
import com.elandt.lil.learningspringnew.util.DateUtils;

@Controller
@RequestMapping("/reservations")
public class RoomReservationController {
    
    private final DateUtils dateUtils;
    private final ReservationService reservationService;
    
    @Autowired
    public RoomReservationController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getReservations(@RequestParam(value = "date", required = false) String dateString, Model model) {
        Date date = this.dateUtils.createDateFromDateString(dateString);
        List<RoomReservation> reservations = this.reservationService.getRoomReservationsForDate(date);
        model.addAttribute("reservations", reservations);
        return "roomres";
    }

}
