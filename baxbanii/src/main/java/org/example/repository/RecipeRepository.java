package org.example.repository;

import org.example.data.entity.Recipe;
import org.example.data.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    public boolean saveRecipe(Recipe recipe) {
        Recipe recipeSaved = entityManager.merge(recipe);
        return entityManager.contains(recipeSaved);
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
}