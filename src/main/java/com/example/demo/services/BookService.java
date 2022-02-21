package com.example.demo.services;

import com.example.demo.models.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    List<Book> bookList = new ArrayList<Book>();

    public List<Book> showAllBooks() {
        return this.bookList;
    }

    public void addNewBook(Book newBook)
    {
        this.bookList.add(newBook);
    }

    public List<Book> searchBookByTitleAndIsbn(String bookIsbn, String bookTitle) {
        return this.bookList.stream().filter(x -> (x.getISBN().contains(bookIsbn) || (x.getTitle().contains(bookTitle)))).collect(Collectors.toList());
    }
}
