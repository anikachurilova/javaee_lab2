package com.example.demo.controllers;

import com.example.demo.controllers.BookController;
import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@WebMvcTest(BookController.class)
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;

    private static List<Book> booksList = new ArrayList<Book>(5);
    private MediaType jsonType = MediaType.APPLICATION_JSON;

    @BeforeEach
    private void _() {
        booksList.add(
                Book.builder()
                        .ISBN("1")
                        .title("Harry Potter")
                        .authorName("Joanne Rowling")
                        .build());
        booksList.add(
                Book.builder()
                        .ISBN("2")
                        .title("Count Monte-Kristo")
                        .authorName("Aleksander Duma")
                        .build());
        booksList.add(
                Book.builder()
                        .ISBN("3")
                        .title("Hearts of three")
                        .authorName("Jack London")
                        .build());
        booksList.add(
                Book.builder()
                        .ISBN("4")
                        .title("Last leave")
                        .authorName("Jack London")
                        .build());
        booksList.add(
                Book.builder()
                        .ISBN("5")
                        .title("Master and Margarit")
                        .authorName("Mykhailo Bulgakov")
                        .build());
    }

    @Test
    public void AddBook_ValidDataPassed_BookAddedSuccessfully() throws Exception {
        Book book = Book.builder().ISBN("100").title("CLR via C#").authorName("Jeffrey Richter").build();
        String json = objectMapper.writeValueAsString(book);
        //String jsonContent = objectMapper.writeValueAsString(booksList.toArray());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/add_book")
                        .contentType(jsonType)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("Success"));
    }

    @Test
    void ShowAllBooks_ValidValuesPassed_BooksReturned() throws Exception
    {
        String jsonContent = objectMapper.writeValueAsString(booksList.toArray());

        Mockito.when(bookService.showAllBooks()).thenReturn(booksList);

        mockMvc.perform(MockMvcRequestBuilders.get("/show_books").contentType(jsonType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonContent));
    }
}