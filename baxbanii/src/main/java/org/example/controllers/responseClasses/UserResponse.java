package org.example.controllers.responseClasses;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String bio;
}
