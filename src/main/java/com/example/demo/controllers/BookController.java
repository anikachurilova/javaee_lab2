package com.example.demo.controllers;

import com.example.demo.models.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    List<Book> bookList = new ArrayList<>();

    @GetMapping("/add_book")
    public String _()
    {
        return "add_book";
    }

    @GetMapping("/show_books")
    public String _(Model model)
    {
        model.addAttribute("bookList", bookList);
        return "show_books";
    }

    @PostMapping("/book_creating")
    public String _(@ModelAttribute Book book)
    {
        bookList.add(book);
        return "redirect:/show_books";
    }
}
