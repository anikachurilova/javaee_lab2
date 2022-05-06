package com.example.demo.configurations;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.demo.services.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService service;
    private final TokenGenerator generator;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain) throws ServletException, IOException {

        final String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        final UserDetails userDetails = service.loadUserByUsername(generator.getUsernameFromToken(token));
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
