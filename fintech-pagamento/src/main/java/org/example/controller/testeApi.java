package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testeApi {
    @GetMapping("/hello")
    public String hello() {
        return "API está funcionando!";
    }
}
