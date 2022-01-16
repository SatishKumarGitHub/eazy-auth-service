package com.ez.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {

        return ResponseEntity.ok("{\"msg\" : \"Welcome Controller\", \"env\":\"local\" , \"name\":\"Mahesh\"}");

    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {

        return ResponseEntity.ok("{\"msg\" : \"Welcome to Dashboard\", \"env\":\"local\" , \"name\":\"Mahesh\"}");

    }
}
