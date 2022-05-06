package com.example.demo.models;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
public class Book {
    @NotEmpty(message = "Title must not be empty!")
    @Column(name = "Title")
    String title;

    @NotEmpty(message = "Book ISBN must not be empty!")
    @Pattern(regexp = "^(\\d{13})?$", message = "Book ISBN must contain exactly 13 digits!")
    @Id
    @Column(name = "ISBN")
    String ISBN;

    @NotEmpty(message = "Author's name must not be empty!")
    @Column(name = "AuthorName")
    String authorName;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<User> users;
}