package ru.authservice.Auth.service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/test")
    public void test() {
        System.out.println("TEST");
    }

    @GetMapping("/test_admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void testAdmin() {
        System.out.println("TEST_admin");
        throw new RuntimeException();
    }
}
