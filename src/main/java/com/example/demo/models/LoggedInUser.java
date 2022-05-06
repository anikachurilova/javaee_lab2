package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(callSuper = true)
public class LoggedInUser extends User {
    public LoggedInUser(final String login, final String password, final List<? extends GrantedAuthority> authorities)
    {
        super(login, password, authorities);
    }
}