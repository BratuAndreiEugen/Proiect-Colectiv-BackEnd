package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.data.entity.Follow;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
@AllArgsConstructor
public class FollowRepository {

    private EntityManager entityManager;


    public void saveFollow(Follow follow) {
        Session session = entityManager.unwrap(Session.class);
        if (follow.getId() == null) {
            session.save(follow);
        } else {
            session.saveOrUpdate(follow);
        }
    }

    public List<Follow> getAllUsersFollowers(Long id) {
        Session session = entityManager.unwrap(Session.class);
        List<Follow> followList = session.createQuery("from Follow where folowerId =: id", Follow.class)
                .setParameter("id", id)
                .getResultList();
        return followList != null ? followList : Collections.emptyList();
    }

    public void deleteFollows(Follow follow) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(follow);
    }


    public Follow getFollow(Long followerId, Long followeeId) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Follow where folowerId = :followerId and foloweeId =: followeeId", Follow.class)
                .setParameter("followerId", followerId)
                .setParameter("followeeId", followeeId)
                .uniqueResult();

    }

}
