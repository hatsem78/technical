package com.technical.challenge.web.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @RequestMapping
    public String technicalChallenge() {
        return "Welcome to the technical challenge";
    }
}
