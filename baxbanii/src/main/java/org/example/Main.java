package org.example;

import org.example.data.entity.Recipe;
import org.example.repository.RecipeRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    RecipeRepository repository;
    @Autowired
    UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public void run(String... args) throws Exception {
        //...
        Recipe recipe = new Recipe(1L,"test","test", 1F,"tet","test","2000-11-11", 1L);
        System.out.println(repository.saveRecipe(recipe));
        System.out.println(repository.getAllRecipes());
        System.out.println(userRepository.getAllUsers());
    }
}