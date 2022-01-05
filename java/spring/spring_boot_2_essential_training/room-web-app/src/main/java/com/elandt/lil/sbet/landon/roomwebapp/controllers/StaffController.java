package com.elandt.lil.sbet.landon.roomwebapp.controllers;

import com.elandt.lil.sbet.landon.roomwebapp.service.StaffService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("staff", staffService.getAllStaff());
        // Name of the view/template to resolve
        return "staff";
    }
}
