package org.example.repository;

import org.example.data.entity.Recipe;
import org.example.data.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Repository
public class RecipeRepository {

    @Autowired
    private EntityManager entityManager;

    /**
     * Returns all recipes from DB
     *
     * @return
     */
    public List<Recipe> getAllRecipes() {
        Session session = entityManager.unwrap(Session.class);
        List<Recipe> recipes = session.createQuery("from Recipe", Recipe.class).getResultList();
        return recipes != null ? recipes : Collections.emptyList();
    }

    /**
     * Add recipe in DB ( true -> added / false -> no)
     *
     * @param recipe
     */
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        return entityManager.merge(recipe);
    }

    /**
     * Returns all recipes for one user
     *
     * @param userId
     * @return
     */
    public List<Recipe> getRecipesByUser(Long userId) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "from Recipe r where r.posterId = :userId";
        List<Recipe> recipes = session.createQuery(hql, Recipe.class)
                .setParameter("userId", userId)
                .getResultList();
        return recipes != null ? recipes : Collections.emptyList();
    }
    /**
     * Returns all recipes that are not his
     *
     * @param userId
     * @return
     */
    public List<Recipe> getRecipesAllThatIsNotUsers(Long userId) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "from Recipe r where r.posterId != :userId";
        List<Recipe> recipes = session.createQuery(hql, Recipe.class)
                .setParameter("userId", userId)
                .getResultList();
        return recipes != null ? recipes : Collections.emptyList();
    }

    /**
     * Returns recipe by id / null (if not found)
     *
     * @param id
     * @return one recipe or null
     */
    public Recipe getRecipeById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Recipe where id = :id", Recipe.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    /**
     * It updates the average grades based on new grades
     *
     * @param id
     * @param healthy grade
     * @param nutritive grade
     * @param taste grade
     * @return
     */
    public Recipe updateRecipeAveragesRatings(Long id, BigDecimal healthy, BigDecimal nutritive, BigDecimal taste) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("update Recipe r " +
                        "set r.healthAverageRating = :healthy," +
                        "r.nutritionAverageRating = :nutritive, " +
                        "r.tasteAverageRating = :taste " +
                        "where r.id = :id", Recipe.class)
                .setParameter("id", id)
                .setParameter("healthy", healthy)
                .setParameter("nutritive", nutritive)
                .setParameter("taste", taste)
                .uniqueResult();
    }
}