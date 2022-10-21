package com.elandt.lil.learningspringnew.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elandt.lil.learningspringnew.business.service.GuestService;

@Controller
@RequestMapping("/guests")
public class GuestController {
    
    private final GuestService guestService;
    
    @Autowired
    public GuestController(GuestService reservationService) {
        this.guestService = reservationService;
    }

    @GetMapping
    public String getGuests(Model model) {
        model.addAttribute("guests", this.guestService.getGuests());
        // This could lead to a circular dependency because we're responding to /guests with a template named 'guests'. That said, it's worked so far as is.
        return "guests";
    }
}
