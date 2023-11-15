package org.example.repository;

import org.example.data.entity.Photo;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
public class PhotoRepository {
    @Autowired
    private EntityManager entityManager;

    /**
     * Returns all photos by recipe id
     *
     * @param recipeId
     * @return
     */
    public List<Photo> getPhotosByRecipeId(Long recipeId) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "from Photo p where p.recipeId = :recipeId";
        List<Photo> photos = session.createQuery(hql, Photo.class)
                .setParameter("recipeId", recipeId)
                .getResultList();
        return photos != null ? photos : Collections.emptyList();

    }
}
