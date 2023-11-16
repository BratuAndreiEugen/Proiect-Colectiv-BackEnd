package org.example;

import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    public static void main(String[] args) {



        SpringApplication.run(Main.class, args);
    }
    public void run(String... args) throws Exception {
        System.out.println(userRepository.getAllUsers());
        //...
    }

}