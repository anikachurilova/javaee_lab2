package com.example.demo.controllers;

import com.example.demo.interfaces.BookRepository;
import com.example.demo.interfaces.UserRepository;
import com.example.demo.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    // List<Book> bookList = new ArrayList<>();
    // private final BookService service;
    private final BookRepository bookRepository;
    private final UserRepository service;

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> addBook(@Valid @RequestBody final Book bookEntity) {
        bookRepository.save(bookEntity);
        List<Book> allBooks = bookRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/show_books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> ShowBooks() {
        List<Book> allBooks = bookRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> SearchBook(@RequestBody final Book bookEntity) {
        String title = bookEntity.getTitle();
        String isbn = bookEntity.getISBN();
        // String authorName = book.getAuthorName();

        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findByISBNContainsAndAndTitleContains(isbn, title));
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/addBookToFavList", method = RequestMethod.POST)
    public void _(@RequestBody final Book book) {
        final LoggedInUser loggedInUser = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User u = service.findByLogin(loggedInUser.getUsername()).get();
        u.getBooks().add(bookRepository.findById(book.getISBN()).get());

        service.save(u);
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/listOfFavBooks", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> _() {
        final LoggedInUser loggedInUser = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(service.findByLogin(loggedInUser.getUsername()).get().getBooks());
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/removeFromFavBook", method = RequestMethod.POST)
    public void deveteFavBook(@RequestBody final Book book) {
        final LoggedInUser authenticatedUser = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User u = service.findByLogin(authenticatedUser.getUsername()).get();
        u.getBooks().remove(bookRepository.findById(book.getISBN()).get());

        service.save(u);
    }
}
