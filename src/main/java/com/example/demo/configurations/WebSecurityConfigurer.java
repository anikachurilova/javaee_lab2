package com.example.demo.configurations;


import static org.springframework.http.HttpMethod.POST;
import com.example.demo.interfaces.UserRepository;
import com.example.demo.services.LoggedInUserService;
import com.example.demo.services.TokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final ObjectMapper objectMapper;
    private final TokenGenerator generator;
    private final UserRepository service;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
                 http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(this.logoutFilter())
                .addFilterBefore(this.loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthorizationFilter(userDetailsService(), generator), PasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new LoggedInUserService(service);
    }

    @Bean
    @SneakyThrows
    @Override
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    private PasswordAuthenticationFilter loginFilter() {
        final PasswordAuthenticationFilter passwordAuthenticationFilter =  new PasswordAuthenticationFilter(authenticationManagerBean(), objectMapper, generator);
        passwordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/logInUser", POST.name()));

        return passwordAuthenticationFilter;
    }

    private LogoutFilter logoutFilter() {
        final LogoutHandler[] logoutHandlers = new LogoutHandler[] {
                new SecurityContextLogoutHandler()
        };

        return new LogoutFilter(new LogoutSuccessHandlerAdapter(), logoutHandlers);
    }
}