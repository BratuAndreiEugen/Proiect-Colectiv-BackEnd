package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {
    @Id
    private Long id;
    private String email;
    private String username;
    private String passwordHash;
    private String bio;

    public User() {

    }
}
