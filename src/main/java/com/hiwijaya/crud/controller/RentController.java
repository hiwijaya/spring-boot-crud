package com.hiwijaya.crud.controller;

import com.hiwijaya.crud.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Happy Indra Wijaya
 */
@Controller
public class RentController {

    @Autowired
    private RentalService rentalService;


    @GetMapping("/rent")
    public String rentPage(Model model){

        model.addAttribute("transactions", rentalService.getAll());

        return "rent";

    }

}
