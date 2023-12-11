package org.example.controllers.requestClasses;

import lombok.Data;
import org.example.data.entity.User;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String bio;

    public UserDTO() { }


    public UserDTO toUserDTO(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setBio(user.getBio());
        return this;
    }
}
