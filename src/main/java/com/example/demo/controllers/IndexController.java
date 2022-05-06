package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.interfaces.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private final BookRepository bookRepository;

    public IndexController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping({ "/", "" })
    public String _() {
        return "index";
    }

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/book/{isbn}")
    public String _(@PathVariable String isbn, @ModelAttribute Book bookEntity, Model model) {
        model.addAttribute("book", bookRepository.findById(isbn));
        return "book";
    }

    @GetMapping("/logInUser")
    public String logIn() {
        return "logInUser";
    }

    @GetMapping("/signUpUser")
    public String signUp() {
        return "signUpUser";
    }
}

