package com.hiwijaya.crud.controller;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.service.BookService;
import com.hiwijaya.crud.service.CustomerService;
import com.hiwijaya.crud.service.RentalService;
import com.hiwijaya.crud.util.BookUnavailableException;
import com.hiwijaya.crud.util.RentDto;
import com.hiwijaya.crud.util.RentOutdatedException;
import com.hiwijaya.crud.util.RentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

        model.addAttribute("transactions", rentalService.getTransactionByStatus(RentStatus.RENT));

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

    @GetMapping("/rent/return/{id}")
    public String returnBook(@PathVariable("id") Long transactionId, Model model){

        RentTransaction transaction = rentalService.getTransaction(transactionId);
        if(transaction == null){
            model.addAttribute("transactions", rentalService.getTransactionByStatus(RentStatus.RENT));
            model.addAttribute("message", "Return failed. Transaction id not found: " + transactionId);

            return "rent";
        }

        try {
            rentalService.returnBooks(transaction);
        } catch (RentOutdatedException e) {
            e.printStackTrace();
        }

        model.addAttribute("transactions", rentalService.getTransactionByStatus(RentStatus.RENT));

        return "rent";

    }

}
