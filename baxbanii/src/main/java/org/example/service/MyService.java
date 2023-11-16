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
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MyService {


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


    public List<Recipe> getAllRecipes() {
        return recipeRepository.getAllRecipes();
    }

    public boolean saveRecipe(Recipe recipe) throws DataChangeException {
        try {
            boolean isSaved = recipeRepository.saveRecipe(recipe);
            if (!isSaved) {
                throw new DataChangeException(new Throwable("Recipe could not be saved"));
            }
            return true;
        } catch (Exception e) {
            //logger.error("Error saving recipe", e);
            throw new DataChangeException(new Throwable("Error saving recipe"));
        }
    }

    public List<Recipe> getRecipesByUser(Long userId) {
        return recipeRepository.getRecipesByUser(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserDTO getUserDTOByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        return user != null ? toUserDTO(user) : null;
    }
}
