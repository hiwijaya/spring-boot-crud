package com.hiwijaya.crud.controller;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    private String bookPage(Model model){
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);

        return "book";
    }


}
