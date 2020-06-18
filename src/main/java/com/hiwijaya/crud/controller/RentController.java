package com.hiwijaya.crud.controller;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.service.BookService;
import com.hiwijaya.crud.service.CustomerService;
import com.hiwijaya.crud.service.RentalService;
import com.hiwijaya.crud.util.BookUnavailableException;
import com.hiwijaya.crud.util.RentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Controller
public class RentController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RentalService rentalService;


    @GetMapping("/rent")
    public String rentPage(Model model){

        model.addAttribute("transactions", rentalService.getAll());

        return "rent";

    }

    @GetMapping("/rent/add")
    public String addRentPage(Model model){

        List<Customer> customers = customerService.getAll();
        List<Book> availableBooks = bookService.getAvailableBooks();
        List<Book> books = bookService.getAll();

        RentDto rentForm = new RentDto();
        rentForm.setBooks(books);

        model.addAttribute("customers", customers);
        model.addAttribute("form", rentForm);

        return "rent-form";

    }


    @PostMapping("/rent")
    public String addRent(@ModelAttribute RentDto form, Model model){

        try {
            rentalService.rent(form);
        } catch (BookUnavailableException e) {
            e.printStackTrace();
        }

        return "redirect:/rent";
    }

}
