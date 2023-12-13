package org.example.repository;

import org.example.data.entity.Rating;
import org.example.data.entity.Recipe;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
public class RatingRepository {
    @Autowired
    private EntityManager entityManager;

    /**
     * Returns all ratings by recipe id
     *
     * @param recipeId
     * @return list of rating
     */
    public List<Rating> getRatingsByRecipeId(Long recipeId) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "from Rating p where p.recipeId = :recipeId";
        List<Rating> ratings = session.createQuery(hql, Rating.class)
                .setParameter("recipeId", recipeId)
                .getResultList();
        return ratings != null ? ratings : Collections.emptyList();
    }

    /**
     * Returns the rating by recipe id and user !should exists only one
     *
     * @param recipeId
     * @param raterId
     * @return rating
     */
    public Rating getRatingsByRecipeIdUser(Long recipeId, Long raterId) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "from Rating p where p.recipeId = :recipeId and p.raterId = :raterId";
        List<Rating> ratings = session.createQuery(hql, Rating.class)
                .setParameter("recipeId", recipeId)
                .setParameter("raterId", raterId)
                .getResultList();
        if (ratings.size() > 1) {
            System.out.println("Ceva bai");
        }
        return ratings.get(0);
    }

    /**
     * Add rating in DB ( true -> added / false -> no)
     * this save can also update an existing rating
     *
     * @param rating
     */
    @Transactional
    public Rating saveRating(Rating rating) {
        Rating oldRating = getRatingsByRecipeIdUser(rating.getRecipeId(), rating.getRaterId());
        if (oldRating != null) {
            update(rating, oldRating);
            return rating;
        }
        return entityManager.merge(rating);
    }

    /**
     * Update the grades for an existing old Rating
     *
     * @param newRating
     * @param oldRating
     */

    private void update(Rating newRating, Rating oldRating) {
        Session session = entityManager.unwrap(Session.class);
        String updateSql = "update Rating r " +
                "set r.healthGrade = :healthRating, " +
                "r.nutritionGrade = :nutritionRating, " +
                "r.tasteGrade = :tasteRating " +
                "where r.raterId = :oldRaterId and " +
                "r.recipeId = :oldRecipeId";
        session.createQuery(updateSql)
                .setParameter("healthRating", newRating.getHealthGrade())
                .setParameter("nutritionRating", newRating.getNutritionGrade())
                .setParameter("tasteRating", newRating.getTasteGrade())
                .setParameter("oldRaterId", oldRating.getRaterId())
                .setParameter("oldRecipeId", oldRating.getRecipeId())
                .executeUpdate();
    }
}
