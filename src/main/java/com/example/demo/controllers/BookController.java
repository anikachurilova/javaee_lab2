package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    // List<Book> bookList = new ArrayList<>();
    private final BookService service;

    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> addBook(@RequestBody final Book newBook) {
        service.addNewBook(newBook);
        List<Book> allBooks = service.showAllBooks();

        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @RequestMapping(value = "/show_books", method = RequestMethod.GET)
    public ResponseEntity<String> _() {
        List<Book> allBooks = service.showAllBooks();

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> _(@RequestBody final Book book) {
        String title = book.getTitle();
        String isbn = book.getISBN();

        return ResponseEntity.status(HttpStatus.OK).body(service.searchBookByTitleAndIsbn(title, isbn));
    }
}
