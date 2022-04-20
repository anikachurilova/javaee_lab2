package com.example.demo.services;

import com.example.demo.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final EntityManager entityManager;

    @Transactional
    public Book addNewBook(Book newBook) {
        return entityManager.merge(newBook);
    }

    public List<Book> showAllBooks() {
        return entityManager
                .createQuery("SELECT book FROM Book book", Book.class)
                .getResultList();
    }

    public Book searchBookByISBN(String ISBN) {
        return entityManager.createQuery("SELECT book FROM Book book WHERE book.ISBN = :ISBN", Book.class)
                .setParameter("ISBN", ISBN)
                .getSingleResult();
    }

    public List<Book> searchBookByISBNOrTitleOrAuthorName(String ISBN, String title, String authorName) {
        return entityManager.createQuery("SELECT book FROM Book book WHERE book.ISBN = :ISBN OR book.title = :title OR book.authorName = :authorName",
                        Book.class)
                .setParameter("ISBN", ISBN)
                .setParameter("title", title)
                .setParameter("authorName", authorName)
                .getResultList();
    }
}
