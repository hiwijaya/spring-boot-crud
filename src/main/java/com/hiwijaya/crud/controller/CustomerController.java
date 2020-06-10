package com.hiwijaya.crud.controller;

import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Happy Indra Wijaya
 */
@Controller
public class CustomerController {

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

    @PostMapping("/customer")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model){

        if(result.hasErrors()){
            return "customer-form";
        }

        customerService.save(customer);

        List<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customerService.getAll());

        return "redirect:/customer";
    }

    @GetMapping("/customer")
    public String customerPage(Model model){
        List<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customerService.getAll());

        return "customer";
    }

    @GetMapping("/customer/add")
    public String addCustomerPage(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("editMode", false);

        return "customer-form";
    }

    @GetMapping("/customer/edit/{id}")
    public String editCustomerPage(@PathVariable("id") Integer id, Model model){

        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("editMode", true);

        return "customer-form";
    }

    @GetMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, Model model){
        Customer customer = customerService.getCustomerById(id);
        if(customer == null){
            model.addAttribute("customers", customerService.getAll());
            model.addAttribute("message", "Delete failed. Invalid customer id: " + id);

            return "customer";
        }

        customerService.delete(customer);
        model.addAttribute("customers", customerService.getAll());

        return "customer";
    }




}
