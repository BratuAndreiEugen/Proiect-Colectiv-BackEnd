package org.example.controllers.requestClasses;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String bio;
}
