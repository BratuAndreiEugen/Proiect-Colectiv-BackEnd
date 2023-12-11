package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.RecipeDTO;
import org.example.controllers.requestClasses.UserDTO;
import org.example.data.entity.Follow;
import org.example.data.entity.Recipe;
import org.example.data.entity.User;
import org.example.exceptions.DataChangeException;
import org.example.repository.FollowRepository;
import org.example.repository.RecipeRepository;
import org.example.repository.UserRepository;
import org.example.validation.ValidateRecipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service(value = "myService")
public class RecipeService {

    private ValidateRecipe validateRecipe;

    private RecipeRepository recipeRepository;

    private UserRepository userRepository;

    private FollowRepository followRepository;


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

    public RecipeDTO getRecipeById(Long recipeId) throws DataChangeException{
        Recipe recipe = recipeRepository.getRecipeById(recipeId);
        if(recipe == null){
            throw new DataChangeException("There is no recipe with this id!");
        }
        return toRecipeDTO(recipe);
    }

    public List<RecipeDTO> getRecipesThatAreNotUsers(Long userId) throws DataChangeException {
        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }
        List<Recipe> recipes = recipeRepository.getRecipesAllThatIsNotUsers(userId);
        if(recipes.isEmpty()){
            throw new DataChangeException("There are no recipes for this user!");
        }
        return recipes.stream().map(this::toRecipeDTO).collect(Collectors.toList());
    }

    public List<RecipeDTO> getCustomRecipeList(Long userId) throws DataChangeException {

        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }

        List<Recipe> allRecipes = recipeRepository.getAllRecipes();

        if(allRecipes.isEmpty()){
            throw new DataChangeException("There are no recipes!");
        }

        return reorganizeRecipes(userId, allRecipes);
    }

    public List<RecipeDTO> getCustomRecipeListPaginate(Long userId, int pageNumber, int pageSize) throws DataChangeException {

        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }

        List<Recipe> allRecipes = recipeRepository.getAllRecipes();

        if(allRecipes.isEmpty()){
            throw new DataChangeException("There are no recipes!");
        }

        List<RecipeDTO> customRecipesList = reorganizeRecipes(userId, allRecipes);

        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, customRecipesList.size());

        if(start > customRecipesList.size()) {
            throw new DataChangeException("Page number is out of range");
        }

        return customRecipesList.subList(start, end);
    }

    private List<RecipeDTO> reorganizeRecipes(Long userId, List<Recipe> allRecipes) {
        List<Follow> followers = followRepository.getAllUsersFollowers(userId);
        List<Long> followedUserIds = followers.stream()
                .map(Follow::getFoloweeId)
                .collect(Collectors.toList());

        List<RecipeDTO> followedRecipes = allRecipes.stream()
                .filter(recipe -> followedUserIds.contains(recipe.getPosterId()))
                .sorted(Comparator.comparing(Recipe::getUploadDate).reversed())
                .map(this::toRecipeDTO)
                .collect(Collectors.toList());

        List<RecipeDTO> otherRecipes = allRecipes.stream()
                .filter(recipe -> !followedUserIds.contains(recipe.getPosterId()))
                .sorted(Comparator.comparing(Recipe::getUploadDate).reversed())
                .map(this::toRecipeDTO)
                .collect(Collectors.toList());

        List<RecipeDTO> customRecipesList = new ArrayList<>();
        customRecipesList.addAll(followedRecipes);
        customRecipesList.addAll(otherRecipes);

        return customRecipesList;
    }



}
