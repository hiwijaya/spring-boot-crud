package com.hiwijaya.crud.controller;

import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Controller
public class WebController {

    @Autowired
    private CustomerService customerService;


    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("message", message);

        return "index";
    }

    @GetMapping("/customer")
    public String customer(Model model){
        List<Customer> customers = customerService.getAll();
        System.out.println("PANJANG CUSTOMERSSSS: " + customers.size());
        model.addAttribute("customers", customerService.getAll());

        return "customer";
    }




}
