package com.example.demo.configurations;

import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.demo.models.LoggedInUser;
import com.example.demo.services.TokenGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.Value;

public class PasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final TokenGenerator generator;

    PasswordAuthenticationFilter(final AuthenticationManager authenticationManager,final ObjectMapper objectMapper,final TokenGenerator generator) {
        this.objectMapper = objectMapper;
        this.generator = generator;
        setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final Credentials credentials = objectMapper.readValue(request.getInputStream(), Credentials.class);
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword(), Arrays.asList());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        final LoggedInUser loggedInUser = (LoggedInUser) auth.getPrincipal();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(loggedInUser));

        response.setHeader(HttpHeaders.AUTHORIZATION, generator.generateToken(loggedInUser));
    }

    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Value
    private static class Credentials {
        private final String login;
        private final String password;
    }

}