package org.example.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.LogInRequest;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/logIn")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LogInController {

    private final UserService service;

    /**
     * Log in a user and generate an authentication token.
     *
     * @param logInRequest LogInRequest object containing username and password.
     * @return ResponseEntity containing an authentication token or an error message if login fails.
     * Example Request: {"userName": "john_doe", "password": "securePassword"}
     * Example Response: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTY0OTExOTQzMCwiZXhwIjoxNjQ5MTIxMDMwfQ.eU5aPqF1-xRBc53zgBTYn82q6eRVl5I1-kIqyJgdd9I"
     */
    @PostMapping
    public ResponseEntity<String> logIn(@RequestBody LogInRequest logInRequest) {
        try {
            service.logIn(logInRequest.getUserName(), logInRequest.getPassword());
            String token = generateToken(logInRequest.getUserName());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Generate a JWT authentication token for a given username.
     *
     * @param username Username for which the token is generated.
     * @return JWT authentication token.
     */
    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256,  Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }
}
