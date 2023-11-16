package org.example.controllers;

import com.mysql.cj.xdevapi.JsonArray;
import lombok.AllArgsConstructor;
import org.example.data.entity.Recipe;
import org.example.exceptions.DataChangeException;
import org.example.service.MyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/recipes")
public class RecipeController {

    private MyService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Recipe> getRecipes(){
        System.out.println("Get all recipes ...");
        //return JSONArray.toJSONString(service.getAllRecipes())
        return service.getAllRecipes();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public List<Recipe> getUserRecipesById(@PathVariable Long userId){
        System.out.println("Get user recipes ...");
        return (List<Recipe>)service.getRecipesByUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Recipe> save(@RequestBody Recipe recipe){
        try {
            service.saveRecipe(recipe);
            return ResponseEntity.ok(recipe);
        } catch (DataChangeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
