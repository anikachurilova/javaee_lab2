package com.example.demo.models;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Login must contain only letters both registers and digits!")
    @Column(name = "login", unique = true)
    private String login;

    @Size(min = 8, max = 20, message = "Password must be 8-20 symbols length!")
    @Column(name = "password")
    private String password;
}
