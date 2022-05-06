package com.example.demo.controllers;

import com.example.demo.enums.PermissionTypes;
import com.example.demo.interfaces.UserRepository;
import com.example.demo.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserRepository service;

    @RequestMapping(value = "/signUpUser", method = RequestMethod.POST)
    public String _(@Valid @RequestBody final User user) {
        Permission u = new Permission();
        u.setPermission(PermissionTypes.AUTHORIZED_USER);
        u.setId(1);

        user.setPermissions(Arrays.asList(u));
        service.save(user);

        return "redirect:/logInUser";
    }
}