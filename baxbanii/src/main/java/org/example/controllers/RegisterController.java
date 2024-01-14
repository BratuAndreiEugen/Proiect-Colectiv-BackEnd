package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.RegisterRequest;
import org.example.data.entity.User;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RequestMapping("/v1/register")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RegisterController {

    private final UserService service;

    /**
     * Register a new user.
     *
     * @param registerRequest RegisterRequest object containing user registration details.
     * @return ResponseEntity indicating successful registration or an error message if registration fails.
     * Example Request: {"username": "john_doe", "email": "john.doe@example.com", "passwordHash": "hashedPassword", "bio": "A bio about John Doe"}
     * Example Response: "ok!" or an error message
     */
    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        try{
            service.registerUser(new User(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPasswordHash(), registerRequest.getBio()));
            return ResponseEntity.ok("ok!");
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
