package org.example.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.LogInRequest;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/logIn")
@AllArgsConstructor
public class LogInController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<String> logIn(@RequestParam String email, @RequestParam String password) {
        try {
            service.logIn(email, password);
            String token = generateToken(email);
            return ResponseEntity.ok(token);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256,  Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }
}
