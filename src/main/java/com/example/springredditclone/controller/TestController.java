package com.example.springredditclone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is used only for testing purposes.
 * Especially to check if the JWT authentication is ok.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String testEndpoint() {
        return "test ok";
    }
}
