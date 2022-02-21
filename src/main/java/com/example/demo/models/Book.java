package com.example.demo.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    String title;
    String ISBN;
    String authorName;
}
