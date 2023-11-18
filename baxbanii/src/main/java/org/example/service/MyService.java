package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.RecipeDTO;
import org.example.controllers.requestClasses.UserDTO;
import org.example.data.entity.Recipe;
import org.example.data.entity.User;
import org.example.exceptions.DataChangeException;
import org.example.repository.RecipeRepository;
import org.example.repository.UserRepository;
import org.example.validation.ValidateRecipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MyService {

    private ValidateRecipe validateRecipe;

    private RecipeRepository recipeRepository;

    private UserRepository userRepository;

    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        return dto;
    }


    public RecipeDTO toRecipeDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setCaption(recipe.getCaption());
        dto.setAverageRating(recipe.getAverageRating());
        dto.setThumbnailLink(recipe.getThumbnailLink());
        dto.setVideoLink(recipe.getVideoLink());
        dto.setUploadDate(recipe.getUploadDate());
        dto.setPosterId(recipe.getPosterId());


        User poster = userRepository.getUserById(recipe.getPosterId());
        dto.setPosterUsername(poster != null ? poster.getUsername() : "Unknown");

        return dto;
    }


    public List<RecipeDTO> getAllRecipes() throws DataChangeException {
        List<Recipe> recipes = recipeRepository.getAllRecipes();
        if(recipes.isEmpty()){
            throw new DataChangeException("There are no recipes!");
        }
        return recipes.stream().map(this::toRecipeDTO).collect(Collectors.toList());
    }

    public Recipe saveRecipe(Recipe recipe) throws DataChangeException {
        try {
            validateRecipe.validateRecipe(recipe);
            Recipe newRecipe = recipeRepository.saveRecipe(recipe);
            if (newRecipe == null) {
                throw new DataChangeException("Recipe could not be saved");
            }
            return newRecipe;
        } catch (DataChangeException e) {
            throw new DataChangeException(e.getMessage());
        }
    }

    public List<RecipeDTO> getRecipesByUser(Long userId) throws DataChangeException {
        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }
        List<Recipe> recipes = recipeRepository.getRecipesByUser(userId);
        if(recipes.isEmpty()){
            throw new DataChangeException("There are no recipes for this user!");
        }
        return recipes.stream().map(this::toRecipeDTO).collect(Collectors.toList());
    }

    public UserDTO getUserDTOByUsername(String username) throws DataChangeException {
        User user = userRepository.getUserByUsername(username);
        if(user == null){
            throw new DataChangeException("There is no user with this username!");
        }
        return toUserDTO(user);
    }

    public RecipeDTO getRecipeById(Long recipeId) throws DataChangeException{
        Recipe recipe = recipeRepository.getRecipeById(recipeId);
        if(recipe == null){
            throw new DataChangeException("There is no recipe with this id!");
        }
        return toRecipeDTO(recipe);
    }
}
