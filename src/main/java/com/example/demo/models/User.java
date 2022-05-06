package com.example.demo.models;

import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @JsonIgnoreProperties("users")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_book", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    @ManyToMany
    @JoinTable(name = "user_to_permissions", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;
}
