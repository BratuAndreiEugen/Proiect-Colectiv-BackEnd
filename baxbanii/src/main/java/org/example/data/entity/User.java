package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String email;
    private String username;
    private String passwordHash;
    private String bio;


    public User(String username, String email, String passwordHash, String bio) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.bio = bio;
    }

    public User() {

    }
}
