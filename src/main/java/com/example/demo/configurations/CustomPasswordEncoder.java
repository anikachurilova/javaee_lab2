package com.example.demo.configurations;

import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}