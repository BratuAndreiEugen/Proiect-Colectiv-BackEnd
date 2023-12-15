package org.example.controllers.requestClasses;

import lombok.Data;
import org.example.data.entity.User;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String bio;
}
