package org.example.controllers;

import com.mysql.cj.xdevapi.JsonArray;
import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.UserDTO;
import org.example.data.entity.Recipe;
import org.example.exceptions.DataChangeException;
import org.example.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/recipes")
public class RecipeController {

    private MyService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getRecipes(){
        System.out.println("Get all recipes ...");
        try{
            List<Recipe> recipes = service.getAllRecipes();
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecipesById(@PathVariable Long userId){
        System.out.println("Get user recipes ...");
        try{
            List<Recipe> recipes = service.getRecipesByUser(userId);
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody Recipe recipe){
        try {
            service.saveRecipe(new Recipe(recipe.getTitle(), recipe.getCaption(), recipe.getAverageRating(), recipe.getThumbnailLink(),
            recipe.getVideoLink(), recipe.getUploadDate(), recipe.getPosterId()));
            return ResponseEntity.ok("ok!");
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {

        try{
            Recipe recipe = service.getRecipeById(id);
            return ResponseEntity.ok(recipe);
        }
        catch (DataChangeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
