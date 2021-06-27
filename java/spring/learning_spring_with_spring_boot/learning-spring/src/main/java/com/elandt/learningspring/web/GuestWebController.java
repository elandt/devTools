package com.elandt.learningspring.web;

import java.util.List;

import com.elandt.learningspring.business.service.ReservationService;
import com.elandt.learningspring.data.entity.Guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guests")
public class GuestWebController {

    private final ReservationService reservationService;

    @Autowired
    public GuestWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getGuests(Model model) {
        List<Guest> guests = this.reservationService.getHotelGuests();
        // Passes the list of guests in the attribute "guests"
        model.addAttribute("guests", guests);
        return "guests";
    }
}
