package org.example.service;
import lombok.AllArgsConstructor;
import org.example.data.entity.User;
import org.example.data.entity.Recipe;
import org.example.controllers.requestClasses.UserDTO;
import org.example.exceptions.DataChangeException;
import org.example.repository.PhotoRepository;
import org.example.repository.RatingRepository;
import org.example.repository.RecipeRepository;
import org.example.repository.UserRepository;
import org.example.validation.ValidateRecipe;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@AllArgsConstructor
@Service
public class MyService {

    private ValidateRecipe validateRecipe;

    private PhotoRepository photoRepository;

    private RatingRepository ratingRepository;

    private RecipeRepository recipeRepository;

    private UserRepository userRepository;

    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        return dto;
    }


    public List<Recipe> getAllRecipes() throws DataChangeException {
        List<Recipe> recipes = recipeRepository.getAllRecipes();
        if(recipes.isEmpty()){
            throw new DataChangeException("There are no recipes!");
        }
        return recipes;
    }

    public boolean saveRecipe(Recipe recipe) throws DataChangeException {
        try {
            validateRecipe.validateRecipe(recipe);
            boolean isSaved = recipeRepository.saveRecipe(recipe);
            if (!isSaved) {
                throw new DataChangeException("Recipe could not be saved");
            }
            return true;
        } catch (DataChangeException e) {
            throw new DataChangeException(e.getMessage());
        }
    }

    public List<Recipe> getRecipesByUser(Long userId) throws DataChangeException {
        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }
        List<Recipe> recipes = recipeRepository.getRecipesByUser(userId);
        if(recipes.isEmpty()){
            throw new DataChangeException("There are no recipes for this user!");
        }
        return recipes;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserDTO getUserDTOByUsername(String username) throws DataChangeException {
        User user = userRepository.getUserByUsername(username);
        if(user == null){
            throw new DataChangeException("There is no user with this username!");
        }
        return toUserDTO(user);
    }

    public Recipe getRecipeById(Long recipeId) throws DataChangeException{
        Recipe recipe = recipeRepository.getRecipeById(recipeId);
        if(recipe == null){
            throw new DataChangeException("There is no recipe with this id!");
        }
        return recipe;
    }
}
