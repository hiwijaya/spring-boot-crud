package com.hiwijaya.crud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Happy Indra Wijaya
 */
@Controller
public class WebController {

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("message", message);

        return "index";
    }




}
