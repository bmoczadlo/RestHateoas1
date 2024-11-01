package com.classcast.services.userservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${test3}")
    private String test3;

    @Value("${test4}")
    private String test4;

    @GetMapping("/")
    String getOne() {
        return "Hello user: " + test3;
    }

    @GetMapping("/users")
    String getAll() {
        return "Hello users: " + test4;
    }
}
