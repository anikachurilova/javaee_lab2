package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private final BookService service;

    public IndexController(BookService service) {
        this.service = service;
    }

    @RequestMapping({ "/", "" })
    public String _() {
        return "index";
    }

    @GetMapping("/book/{isbn}")
    public String _(@PathVariable String isbn, @ModelAttribute Book _, Model model) {
        model.addAttribute("book", service.searchBookByISBN(isbn));
        return "book";
    }
}

