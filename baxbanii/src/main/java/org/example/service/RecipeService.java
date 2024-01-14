package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controllers.responseClasses.RecipeResponse;
import org.example.data.entity.Follow;
import org.example.data.entity.Recipe;
import org.example.data.entity.User;
import org.example.exceptions.DataChangeException;
import org.example.repository.FollowRepository;
import org.example.controllers.responseClasses.UserResponse;
import org.example.data.entity.Rating;
import org.example.repository.RatingRepository;
import org.example.repository.RecipeRepository;
import org.example.repository.UserRepository;
import org.example.validation.ValidateRating;
import org.example.validation.ValidateRecipe;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.*;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service(value = "myService")
public class RecipeService {

    private ValidateRecipe validateRecipe;

    private RecipeRepository recipeRepository;

    private UserRepository userRepository;
    private ValidateRating validateRating;
    private RatingRepository ratingRepository;

    private FollowRepository followRepository;

    /**
     * Convert a Recipe entity to a RecipeResponse DTO.
     *
     * @param recipe Recipe entity to convert.
     * @return RecipeResponse DTO.
     */

    public RecipeResponse toRecipeDTO(Recipe recipe) {
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setTitle(recipe.getTitle());
        recipeResponse.setCaption(recipe.getCaption());
        recipeResponse.setAverageRating(recipe.getAverageRating());
        recipeResponse.setThumbnailLink(recipe.getThumbnailLink());
        recipeResponse.setVideoLink(recipe.getVideoLink());
        recipeResponse.setUploadDate(recipe.getUploadDate());
        recipeResponse.setPosterId(recipe.getPosterId());
        recipeResponse.setAverageRating(recipe.getAverageRating());
        recipeResponse.setHealthAverageRating(recipe.getHealthAverageRating());
        recipeResponse.setNutritionAverageRating(recipe.getNutritionAverageRating());
        recipeResponse.setTasteAverageRating(recipe.getTasteAverageRating());
        User poster = userRepository.getUserById(recipe.getPosterId());
        recipeResponse.setPosterUsername(poster != null ? poster.getUsername() : "Unknown");

        return recipeResponse;
    }
    /**
     * Convert a User entity to a UserResponse DTO.
     *
     * @param user User entity to convert.
     * @return UserResponse DTO.
     */
    public UserResponse toUserDTO(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        return dto;
    }

    /**
     * Get all recipes, and convert them to RecipeResponse DTOs.
     *
     * @return List of RecipeResponse DTOs.
     * @throws DataChangeException If there are no recipes.
     */
    public List<RecipeResponse> getAllRecipes() throws DataChangeException {
        List<Recipe> recipes = recipeRepository.getAllRecipes();
        if (recipes.isEmpty()) {
            throw new DataChangeException("There are no recipes!");
        }
        return recipes.stream().map(this::toRecipeDTO).collect(Collectors.toList());
    }
    /**
     * Save a new recipe and perform validation.
     *
     * @param recipe Recipe entity to save.
     * @return Saved Recipe entity.
     * @throws DataChangeException If the recipe could not be saved or validation fails.
     */
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

    /**
     * Save a new rating for a recipe and perform validation.
     *
     * @param rating Rating entity to save.
     * @return Saved Rating entity.
     * @throws Exception If the rating could not be saved or validation fails.
     */
    public Rating saveRating(Rating rating) throws Exception {
        try {
            validateRating.validateRating(rating);
            Rating newRating = ratingRepository.saveRating(rating);
            if (newRating == null) {
                throw new Exception("Recipe could not be saved");
            }

            // Se intampla cv cu tranzactiile aici : in loc sa ia in calcul la medie rating-ul nou, il ia pe cel vechi, ca si cum nu
            // ar fi comisa tranzactia
            //computeRatingAverage(newRating.getRecipeId());
            return newRating;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Get recipes posted by a specific user.
     *
     * @param userId User ID for whom recipes are retrieved.
     * @return List of RecipeResponse DTOs.
     * @throws DataChangeException If there are no recipes for the user.
     */
    public List<RecipeResponse> getRecipesByUser(Long userId) throws DataChangeException {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new DataChangeException("There is no user with this id!");
        }
        List<Recipe> recipes = recipeRepository.getRecipesByUser(userId);
        if (recipes.isEmpty()) {
            throw new DataChangeException("There are no recipes for this user!");
        }
        return recipes.stream().map(this::toRecipeDTO).collect(Collectors.toList());
    }

    /**
     * Get UserResponse DTO by username.
     *
     * @param username Username to search for.
     * @return UserResponse DTO.
     * @throws DataChangeException If there is no user with the given username.
     */
    public UserResponse getUserDTOByUsername(String username) throws DataChangeException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new DataChangeException("There is no user with this username!");
        }
        return toUserDTO(user);
    }
    /**
     * Get RecipeResponse DTO by recipe ID.
     *
     * @param recipeId Recipe ID to search for.
     * @return RecipeResponse DTO.
     * @throws DataChangeException If there is no recipe with the given ID.
     */
    public RecipeResponse getRecipeById(Long recipeId) throws DataChangeException {

        Recipe recipe = recipeRepository.getRecipeById(recipeId);
        if (recipe == null) {
            throw new DataChangeException("There is no recipe with this id!");
        }
        return toRecipeDTO(recipe);
    }
    /**
     * Get recipes that are not posted by the given user.
     *
     * @param userId User ID for whom recipes are excluded.
     * @return List of RecipeResponse DTOs.
     * @throws DataChangeException If there are no recipes for the user.
     */
    public List<RecipeResponse> getRecipesThatAreNotUsers(Long userId) throws DataChangeException {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new DataChangeException("There is no user with this id!");
        }
        List<Recipe> recipes = recipeRepository.getRecipesAllThatIsNotUsers(userId);
        if (recipes.isEmpty()) {
            throw new DataChangeException("There are no recipes for this user!");
        }
        return recipes.stream().map(this::toRecipeDTO).collect(Collectors.toList());
    }

    /**
     * Get a customized list of recipes based on user preferences.
     *
     * @param userId User ID for whom recipes are customized.
     * @return List of RecipeResponse DTOs.
     * @throws DataChangeException If there are no recipes or users.
     */
    public List<RecipeResponse> getCustomRecipeList(Long userId) throws DataChangeException {

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
    /**
     * Get a paginated, customized list of recipes based on user preferences.
     *
     * @param userId     User ID for whom recipes are customized.
     * @param pageNumber Page number for pagination.
     * @param pageSize   Number of recipes per page.
     * @return List of RecipeResponse DTOs for the specified page.
     * @throws DataChangeException If there are no recipes or the page number is out of range.
     */
    public List<RecipeResponse> getCustomRecipeListPaginate(Long userId, int pageNumber, int pageSize) throws DataChangeException {

        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }

        List<Recipe> allRecipes = recipeRepository.getAllRecipes();

        if(allRecipes.isEmpty()){
            throw new DataChangeException("There are no recipes!");
        }

        List<RecipeResponse> customRecipesList = reorganizeRecipes(userId, allRecipes);

        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, customRecipesList.size());

        if(start > customRecipesList.size()) {
            throw new DataChangeException("Page number is out of range");
        }

        return customRecipesList.subList(start, end);
    }

    /**
     * Reorganize the list of recipes based on user preferences and followed users.
     * Followed users' recipes are shuffled and placed first, followed by other users' recipes.
     *
     * @param userId     User ID for whom recipes are customized.
     * @param allRecipes List of all recipes available.
     * @return List of RecipeResponse DTOs with reorganized recipes.
     */
    private List<RecipeResponse> reorganizeRecipes(Long userId, List<Recipe> allRecipes) {
        List<Follow> followers = followRepository.getAllFollowsReceivedByUser(userId);

        List<Long> followedUserIds = followers.stream()
                .map(Follow::getFolowerId)
                .collect(Collectors.toList());

        List<RecipeResponse> followedRecipes = allRecipes.stream()
                .filter(recipe -> followedUserIds.contains(recipe.getPosterId()) )
                .sorted(Comparator.comparing(Recipe::getUploadDate).reversed())
                .map(this::toRecipeDTO)
                .collect(Collectors.toList());

        Collections.shuffle(followedRecipes, new Random());

        List<RecipeResponse> otherRecipes = allRecipes.stream()
                .filter(recipe -> !followedUserIds.contains(recipe.getPosterId()) && !recipe.getPosterId().equals(userId))
                .sorted(Comparator.comparing(Recipe::getUploadDate).reversed())
                .map(this::toRecipeDTO)
                .collect(Collectors.toList());

        List<RecipeResponse> customRecipesList = new ArrayList<>();
        customRecipesList.addAll(followedRecipes);
        customRecipesList.addAll(otherRecipes);

        return customRecipesList;
    }


    /**
     * Compute the average rating for a recipe based on existing ratings.
     *
     * @param recipeId Recipe ID for which the average rating is computed.
     * @return Updated Recipe entity.
     * @throws Exception If there is no recipe with the given ID.
     */
    public Recipe computeRatingAverage(Long recipeId) throws Exception {
        Recipe recipe = recipeRepository.getRecipeById(recipeId);
        if (recipe == null) {
            throw new Exception("No recipe when compute rating!");
        }

        List<Rating> ratingListByRecipe = ratingRepository.getRatingsByRecipeId(recipeId);

        BigDecimal healthSum = BigDecimal.ZERO;
        BigDecimal nutritiveSum = BigDecimal.ZERO;
        BigDecimal tasteSum = BigDecimal.ZERO;

        for (Rating rating : ratingListByRecipe) {
            healthSum = healthSum.add(BigDecimal.valueOf(rating.getHealthGrade()));
            nutritiveSum = nutritiveSum.add(BigDecimal.valueOf(rating.getNutritionGrade()));
            tasteSum = tasteSum.add(BigDecimal.valueOf(rating.getTasteGrade()));
        }

        BigDecimal numberOfRatings = BigDecimal.valueOf(ratingListByRecipe.size());

        // Calculate averages
        BigDecimal averageHealth = healthSum.divide(numberOfRatings, RoundingMode.HALF_UP);
        BigDecimal averageNutritive = nutritiveSum.divide(numberOfRatings, RoundingMode.HALF_UP);
        BigDecimal averageTaste = tasteSum.divide(numberOfRatings, RoundingMode.HALF_UP);

        recipeRepository.updateRecipeAveragesRatings(recipeId, averageHealth, averageNutritive, averageTaste);
        return recipeRepository.getRecipeById(recipeId);
    }

    /**
     * Get the rating given by a specific user for a recipe.
     *
     * @param recipeId Recipe ID.
     * @param raterId  User ID of the rater.
     * @return Rating entity.
     */
    public Rating getRating(Long recipeId, Long raterId){
        return ratingRepository.getRatingsByRecipeIdUser(recipeId, raterId);
    }

}
