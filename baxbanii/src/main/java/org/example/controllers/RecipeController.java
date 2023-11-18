package org.example.controllers;

import com.mysql.cj.xdevapi.JsonArray;
import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.RecipeDTO;
import org.example.controllers.requestClasses.UserDTO;
import org.example.data.entity.Recipe;
import org.example.exceptions.DataChangeException;
import org.example.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    private MyService service;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<?> getRecipes(){
        System.out.println("Get all recipes ...");
        try{
            List<RecipeDTO> recipes = service.getAllRecipes();
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecipesById(@PathVariable Long userId){
        System.out.println("Get user recipes ...");
        try{
            List<RecipeDTO> recipesDTO = service.getRecipesByUser(userId);
            return ResponseEntity.ok(recipesDTO);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody Recipe recipe){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Float rating = 0.0f;
        try {
            service.saveRecipe(new Recipe(recipe.getTitle(), recipe.getCaption(), rating, recipe.getThumbnailLink(),
                    recipe.getVideoLink(), date, recipe.getPosterId()));
            return ResponseEntity.ok("ok!");
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {

        try{
            RecipeDTO recipeDTO = service.getRecipeById(id);
            return ResponseEntity.ok(recipeDTO);
        }
        catch (DataChangeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
