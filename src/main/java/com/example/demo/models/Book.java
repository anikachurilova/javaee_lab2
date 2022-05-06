package com.example.demo.models;
import lombok.*;

import javax.persistence.*;
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

    @Column(name = "Title")
    String title;

    @Id
    @Column(name = "ISBN")
    String ISBN;

    @Column(name = "AuthorName")
    String authorName;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<User> users;
}