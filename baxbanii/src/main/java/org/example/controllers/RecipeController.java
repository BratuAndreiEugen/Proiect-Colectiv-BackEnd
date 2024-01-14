package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.RatingRequest;
import org.example.controllers.responseClasses.RecipeResponse;
import org.example.controllers.requestClasses.RecipeRequest;
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


@AllArgsConstructor
@RestController
@RequestMapping("/v1/recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    private RecipeService recipeService;

    /**
     * Retrieves all recipes.
     *
     * @return ResponseEntity containing a list of RecipeDTOs or an error message if data retrieval fails.
     * <p>
     * example of request:
     * GET: /v1/recipes
     * example of response:
     * [
     * {
     * "id": 1,
     * "title": "Delicious Pasta",
     * "caption": "A mouthwatering pasta recipe",
     * "averageRating": 4.5,
     * "thumbnailLink": "https://example.com/pasta-thumbnail.jpg",
     * "videoLink": "https://example.com/pasta-video.mp4",
     * "uploadDate": "2022-01-14",
     * "posterId": 123,
     * "posterUsername": "foodie123",
     * "healthAverageRating": 4.2,
     * "nutritionAverageRating": 4.5,
     * "tasteAverageRating": 4.8
     * },
     * {
     * "id": 2,
     * "title": "Healthy Salad",
     * "caption": "A nutritious salad recipe",
     * "averageRating": 4.0,
     * "thumbnailLink": "https://example.com/salad-thumbnail.jpg",
     * "videoLink": "https://example.com/salad-video.mp4",
     * "uploadDate": "2022-01-15",
     * "posterId": 456,
     * "posterUsername": "veggie_lover",
     * "healthAverageRating": 4.5,
     * "nutritionAverageRating": 3.8,
     * "tasteAverageRating": 4.2
     * },
     * {
     * "id": 3,
     * "title": "Chocolate Cake",
     * "caption": "An indulgent chocolate cake recipe",
     * "averageRating": 4.8,
     * "thumbnailLink": "https://example.com/cake-thumbnail.jpg",
     * "videoLink": "https://example.com/cake-video.mp4",
     * "uploadDate": "2022-01-16",
     * "posterId": 789,
     * "posterUsername": "sweet_tooth",
     * "healthAverageRating": 3.5,
     * "nutritionAverageRating": 4.0,
     * "tasteAverageRating": 4.9
     * }
     * ]
     */
    @GetMapping
    public ResponseEntity<?> getRecipes() {
        System.out.println("Get all recipes ...");
        try {
            List<RecipeResponse> recipes = recipeService.getAllRecipes();
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retrieves recipes for a specific user based on user ID.
     *
     * @param userId User ID to identify the user.
     * @return ResponseEntity containing a list of RecipeDTOs or an error message if data retrieval fails.
     * <p>
     * example of request:
     * GET: /v1/recipes/user/1
     * [
     * {
     * "id": 1,
     * "title": "Delicious Pasta",
     * "caption": "A mouthwatering pasta recipe",
     * "averageRating": 4.5,
     * "thumbnailLink": "https://example.com/pasta-thumbnail.jpg",
     * "videoLink": "https://example.com/pasta-video.mp4",
     * "uploadDate": "2022-01-14",
     * "posterId": 1,
     * "posterUsername": "foodie123",
     * "healthAverageRating": 4.2,
     * "nutritionAverageRating": 4.5,
     * "tasteAverageRating": 4.8
     * }
     * ]
     */
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> getUserRecipesById(@PathVariable Long userId) {
        System.out.println("Get user recipes ...");
        try {
            List<RecipeResponse> recipesDTO = recipeService.getRecipesByUser(userId);
            return ResponseEntity.ok(recipesDTO);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retrieves recipes that do not belong to a specific user based on user ID.
     *
     * @param userId User ID to identify the user.
     * @return ResponseEntity containing a list of RecipeDTOs or an error message if data retrieval fails.
     * <p>
     * example of request:
     * GET: /v1/recipes/user/not/1
     * [
     * {
     * "id": 1,
     * "title": "Delicious Pasta",
     * "caption": "A mouthwatering pasta recipe",
     * "averageRating": 4.5,
     * "thumbnailLink": "https://example.com/pasta-thumbnail.jpg",
     * "videoLink": "https://example.com/pasta-video.mp4",
     * "uploadDate": "2022-01-14",
     * "posterId": 2,
     * "posterUsername": "foodie123",
     * "healthAverageRating": 4.2,
     * "nutritionAverageRating": 4.5,
     * "tasteAverageRating": 4.8
     * }
     * ]
     */
    @RequestMapping(value = "/user/not/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserRecipesNotById(@PathVariable Long userId) {
        try {
            List<RecipeResponse> recipesDTO = recipeService.getRecipesThatAreNotUsers(userId);
            return ResponseEntity.ok(recipesDTO);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * Saves a new recipe.
     *
     * @param recipe Recipe object to be saved.
     * @return ResponseEntity containing the saved recipe ID or an error message if the save operation fails.
     * <p>
     * POST: /v1/recipes/save
     * {
     * "title": "Spicy Chicken Curry",
     * "caption": "A delicious and spicy chicken curry recipe",
     * "averageRating": 0.0,
     * "thumbnailLink": "https://example.com/chicken-curry-thumbnail.jpg",
     * "videoLink": "https://example.com/chicken-curry-video.mp4",
     * "uploadDate": "2023-05-20",
     * "posterId": 567,
     * "healthAverageRating": 4.5,
     * "nutritionAverageRating": 4.0,
     * "tasteAverageRating": 4.8
     * }
     * <p>
     * respose:
     * a new id for the receipe: 123
     * else:
     * 400 - error message
     */
    @PostMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody RecipeRequest recipe) {
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

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id Recipe ID to identify the recipe.
     * @return ResponseEntity containing the RecipeDTO or an error message if data retrieval fails.
     * <p>
     * Request:
     * GET: /v1/recipes/1
     * Response:
     * [
     * {
     * "id": 1,
     * "title": "Delicious Pasta",
     * "caption": "A mouthwatering pasta recipe",
     * "averageRating": 4.5,
     * "thumbnailLink": "https://example.com/pasta-thumbnail.jpg",
     * "videoLink": "https://example.com/pasta-video.mp4",
     * "uploadDate": "2022-01-14",
     * "posterId": 2,
     * "posterUsername": "foodie123",
     * "healthAverageRating": 4.2,
     * "nutritionAverageRating": 4.5,
     * "tasteAverageRating": 4.8
     * }
     * ]
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {

        try {
            RecipeResponse recipeResponse = recipeService.getRecipeById(id);
            return ResponseEntity.ok(recipeResponse);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retrieves a custom list of recipes for a user.
     *
     * @param userId User ID to identify the user.
     * @return ResponseEntity containing a list of RecipeDTOs or an error message if data retrieval fails.
     * <p>
     * GET: /v1/recipes/getAllRecipes/1
     * response:
     * * [
     * {
     * "id": 1,
     * "title": "Delicious Pasta",
     * "caption": "A mouthwatering pasta recipe",
     * "averageRating": 4.5,
     * "thumbnailLink": "https://example.com/pasta-thumbnail.jpg",
     * "videoLink": "https://example.com/pasta-video.mp4",
     * "uploadDate": "2022-01-14",
     * "posterId": 123,
     * "posterUsername": "foodie123",
     * "healthAverageRating": 4.2,
     * "nutritionAverageRating": 4.5,
     * "tasteAverageRating": 4.8
     * },
     * {
     * "id": 2,
     * "title": "Healthy Salad",
     * "caption": "A nutritious salad recipe",
     * "averageRating": 4.0,
     * "thumbnailLink": "https://example.com/salad-thumbnail.jpg",
     * "videoLink": "https://example.com/salad-video.mp4",
     * "uploadDate": "2022-01-15",
     * "posterId": 456,
     * "posterUsername": "veggie_lover",
     * "healthAverageRating": 4.5,
     * "nutritionAverageRating": 3.8,
     * "tasteAverageRating": 4.2
     * },
     * {
     * "id": 3,
     * "title": "Chocolate Cake",
     * "caption": "An indulgent chocolate cake recipe",
     * "averageRating": 4.8,
     * "thumbnailLink": "https://example.com/cake-thumbnail.jpg",
     * "videoLink": "https://example.com/cake-video.mp4",
     * "uploadDate": "2022-01-16",
     * "posterId": 789,
     * "posterUsername": "sweet_tooth",
     * "healthAverageRating": 3.5,
     * "nutritionAverageRating": 4.0,
     * "tasteAverageRating": 4.9
     * }
     * ]
     */
    @GetMapping(value = "/getAllRecipes/{userId}")
    public ResponseEntity<?> getRecipesCustom(@PathVariable Long userId) {
        try {
            List<RecipeResponse> recipes = recipeService.getCustomRecipeList(userId);
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * Retrieves a paginated custom list of recipes for a user.
     *
     * @param userId     User ID to identify the user.
     * @param pageNumber Page number for pagination.
     * @param pageSize   Number of items per page.
     * @return ResponseEntity containing a list of RecipeDTOs or an error message if data retrieval fails.
     * <p>
     * Request:
     * GET: /v1/recipes/getRecipesPage/1/1/3
     * <p>
     * * [
     * {
     * "id": 1,
     * "title": "Delicious Pasta",
     * "caption": "A mouthwatering pasta recipe",
     * "averageRating": 4.5,
     * "thumbnailLink": "https://example.com/pasta-thumbnail.jpg",
     * "videoLink": "https://example.com/pasta-video.mp4",
     * "uploadDate": "2022-01-14",
     * "posterId": 123,
     * "posterUsername": "foodie123",
     * "healthAverageRating": 4.2,
     * "nutritionAverageRating": 4.5,
     * "tasteAverageRating": 4.8
     * },
     * {
     * "id": 2,
     * "title": "Healthy Salad",
     * "caption": "A nutritious salad recipe",
     * "averageRating": 4.0,
     * "thumbnailLink": "https://example.com/salad-thumbnail.jpg",
     * "videoLink": "https://example.com/salad-video.mp4",
     * "uploadDate": "2022-01-15",
     * "posterId": 456,
     * "posterUsername": "veggie_lover",
     * "healthAverageRating": 4.5,
     * "nutritionAverageRating": 3.8,
     * "tasteAverageRating": 4.2
     * },
     * {
     * "id": 3,
     * "title": "Chocolate Cake",
     * "caption": "An indulgent chocolate cake recipe",
     * "averageRating": 4.8,
     * "thumbnailLink": "https://example.com/cake-thumbnail.jpg",
     * "videoLink": "https://example.com/cake-video.mp4",
     * "uploadDate": "2022-01-16",
     * "posterId": 789,
     * "posterUsername": "sweet_tooth",
     * "healthAverageRating": 3.5,
     * "nutritionAverageRating": 4.0,
     * "tasteAverageRating": 4.9
     * }
     * ]
     */
    @GetMapping(value = "/getRecipesPage/{userId}/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getCustomRecipesPaginated(@PathVariable Long userId,
                                                       @PathVariable int pageNumber,
                                                       @PathVariable int pageSize) {
        try {
            List<RecipeResponse> recipes = recipeService.getCustomRecipeListPaginate(userId, pageNumber, pageSize);
            return ResponseEntity.ok(recipes);
        } catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Saves a rating for a specific recipe.
     *
     * @param recipeId      Recipe ID to identify the recipe.
     * @param ratingRequest RatingRequest object containing user and rating details.
     * @return ResponseEntity containing the saved rating or an error message if the operation fails.
     * <p>
     * POST: /v1/recipes/1/rating
     * <p>
     * {
     * "userId": 789,
     * "healthy": 4,
     * "nutritive": 5,
     * "tasty": 4
     * }
     * <p>
     * Response:
     * {
     * "id": 1,
     * "recipeId": 123,
     * "raterId": 456,
     * "healthGrade": 4,
     * "nutritionGrade": 5,
     * "tasteGrade": 4
     * }
     */
    @PostMapping(value = "/{recipeId}/rating")
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

    /**
     * Calculates and updates the average rating for a specific recipe.
     *
     * @param recipeId Recipe ID to identify the recipe.
     * @return ResponseEntity containing the calculated average rating or an error message if the operation fails.
     *
     * Request:
     *PUT: /v1/recipes/1/averageRating
     *reposnse:
     * 1
     */
    @PutMapping(value = "/{recipeId}/averageRating")
    public ResponseEntity<?> calculateAverageRating(@PathVariable Long recipeId) {
        try {
            return ResponseEntity.ok(recipeService.computeRatingAverage(recipeId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    /**
     * Retrieves the rating for a specific recipe by a user.
     *
     * @param recipeId Recipe ID to identify the recipe.
     * @param userId   User ID to identify the user.
     * @return ResponseEntity containing the rating or an error message if the operation fails.
     *
     *GET: v1/recipes/1/1
     *
     * Response:
     *{
     *   "id": 1,
     *   "recipeId": 123,
     *   "raterId": 456,
     *   "healthGrade": 4,
     *   "nutritionGrade": 5,
     *   "tasteGrade": 4
     * }
     */
    @GetMapping(value = "/rating/{recipeId}/{userId}")
    public ResponseEntity<?> getRating(@PathVariable Long recipeId, @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(recipeService.getRating(recipeId, userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
