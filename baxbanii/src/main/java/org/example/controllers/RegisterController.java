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
