package com.example.demo.services;

import com.example.demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByISBNContainsAndAndTitleContains(String ISBN, String title);
}