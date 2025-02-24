package com.trustrace.tiles_hub_be.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600) // Allow cross-origin requests from any origin for 1 hour
@RestController // Indicate that this class is a REST controller
@RequestMapping("/api/test") // Base URL for test-related endpoints
public class TestController {

    /**
     * Public endpoint that can be accessed without any authentication.
     *
     * @return A string message indicating public content.
     */
    @GetMapping("/all") // Map GET requests to "/api/test/all"
    public String allAccess() {
        return "Public Content."; // Return a message accessible by anyone
    }
}
