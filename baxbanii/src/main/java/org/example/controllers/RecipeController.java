package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.RatingRequest;
import org.example.controllers.requestClasses.RecipeDTO;
import org.example.data.entity.Rating;
import org.example.data.entity.Recipe;
import org.example.exceptions.DataChangeException;
import org.example.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<?> getRecipes() {
        System.out.println("Get all recipes ...");
        try {
            List<RecipeDTO> recipes = recipeService.getAllRecipes();
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecipesById(@PathVariable Long userId) {
        System.out.println("Get user recipes ...");
        try {
            List<RecipeDTO> recipesDTO = recipeService.getRecipesByUser(userId);
            return ResponseEntity.ok(recipesDTO);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @RequestMapping(value = "/user/not/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecipesNotById(@PathVariable Long userId) {
        try {
            List<RecipeDTO> recipesDTO = recipeService.getRecipesThatAreNotUsers(userId);
            return ResponseEntity.ok(recipesDTO);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody Recipe recipe) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Float rating = 0.0f;
        try {
            Recipe newRecipe = recipeService.saveRecipe(new Recipe(recipe.getTitle(), recipe.getCaption(), rating, recipe.getThumbnailLink(),
                    recipe.getVideoLink(), date, recipe.getPosterId()));
            return ResponseEntity.ok(newRecipe.getId().toString());
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {

        try {
            RecipeDTO recipeDTO = recipeService.getRecipeById(id);
            return ResponseEntity.ok(recipeDTO);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getAllRecipes/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecipesCustom(@PathVariable Long userId) {
        try {
            List<RecipeDTO> recipes = recipeService.getCustomRecipeList(userId);
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getRecipesPage/{userId}/{pageNumber}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomRecipesPaginated(@PathVariable Long userId,
                                                       @PathVariable int pageNumber,
                                                       @PathVariable int pageSize) {
        try {
            List<RecipeDTO> recipes = recipeService.getCustomRecipeListPaginate(userId, pageNumber, pageSize);
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{recipeId}/rating", method = RequestMethod.POST)
    public ResponseEntity<String> saveRating(@PathVariable Long recipeId, @RequestBody RatingRequest ratingRequest) {
        try {
            Rating newRating = recipeService.saveRating(new Rating(recipeId,
                    ratingRequest.getUserId(),
                    ratingRequest.getHealthy(),
                    ratingRequest.getNutritive(),
                    ratingRequest.getTasty()));
            return ResponseEntity.ok(newRating.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{recipeId}/averageRating", method = RequestMethod.PUT)
    public ResponseEntity<?> calculateAverageRating(@PathVariable Long recipeId)
    {
        try{
            return ResponseEntity.ok(recipeService.computeRatingAverage(recipeId));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/rating/{recipeId}/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRating(@PathVariable Long recipeId, @PathVariable Long userId){
        try{
            return ResponseEntity.ok(recipeService.getRating(recipeId, userId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
