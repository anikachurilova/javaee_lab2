package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.models.LoggedInUser;
import com.example.demo.models.Permission;
import com.example.demo.models.User;
import com.example.demo.interfaces.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoggedInUserService implements UserDetailsService {

    private final UserRepository service;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User u = service.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Unable to find user with login '" + username + "'"));
        return new LoggedInUser(username, u.getPassword(), this.mapAuthorities(u.getPermissions()));
    }

    private static List<GrantedAuthority> mapAuthorities(final List<Permission> permissions) {
        return permissions.stream().map(Permission::getPermission).map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}