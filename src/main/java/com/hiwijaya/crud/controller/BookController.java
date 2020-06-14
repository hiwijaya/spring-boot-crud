package com.hiwijaya.crud.controller;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    private String addBook(@Valid Book book, BindingResult result, Model model){

        if(result.hasErrors()){
            return "book-form";
        }

        bookService.save(book);

        model.addAttribute("books", bookService.getAll());

        return "redirect:/book";
    }

    @GetMapping("/book")
    private String bookPage(Model model){
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);

        return "book";
    }

    @GetMapping("/book/add")
    public String addBookPage(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("editMode", false);

        return "book-form";
    }

    @GetMapping("/book/edit/{id}")
    public String editBookPage(@PathVariable("id") Integer id, Model model){

        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("editMode", true);

        return "book-form";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBookr(@PathVariable("id") Integer id, Model model){
        Book book = bookService.getBookById(id);
        if(book == null){
            model.addAttribute("books", bookService.getAll());
            model.addAttribute("message", "Delete failed. Invalid book id: " + id);

            return "book";
        }

        bookService.delete(book);
        model.addAttribute("books", bookService.getAll());

        return "book";
    }




}
