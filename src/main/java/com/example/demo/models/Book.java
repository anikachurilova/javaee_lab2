package com.example.demo.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {
    @Column(name = "Title")
    String title;

    @Id
    @Column(name = "ISBN")
    String ISBN;

    @Column(name = "AuthorName")
    String authorName;
}
