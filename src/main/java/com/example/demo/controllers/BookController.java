package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.services.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    // List<Book> bookList = new ArrayList<>();
    // private final BookService service;
    private final BookRepository bookRepository;

    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> addBook(@RequestBody final Book newBook) {
        bookRepository.save(newBook);
        List<Book> allBooks = bookRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @RequestMapping(value = "/show_books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> ShowBooks() {
        List<Book> allBooks = bookRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> SearchBook(@RequestBody final Book book) {
        String title = book.getTitle();
        String isbn = book.getISBN();
        // String authorName = book.getAuthorName();

        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findByISBNContainsAndAndTitleContains(isbn, title));
    }
}
