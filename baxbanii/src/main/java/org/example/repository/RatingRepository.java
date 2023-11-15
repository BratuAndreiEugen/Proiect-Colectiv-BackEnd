package org.example.repository;

import org.example.data.entity.Rating;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
     * @return
     */
    public List<Rating> getRatingsByRecipeId(Long recipeId) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "from Rating p where p.recipeId = :recipeId";
        List<Rating> ratings = session.createQuery(hql, Rating.class)
                .setParameter("recipeId", recipeId)
                .getResultList();
        return ratings != null ? ratings : Collections.emptyList();
    }
}
