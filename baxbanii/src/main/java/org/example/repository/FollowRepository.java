package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.data.entity.Follow;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

    public List<Follow> getAllFollowsByUser(Long id) {
        Session session = entityManager.unwrap(Session.class);
        List<Follow> followList = session.createQuery("from Follow where folowerId =: id", Follow.class)
                .setParameter("id", id)
                .getResultList();
        return followList != null ? followList : Collections.emptyList();
    }

    public void deleteFollows(Follow follow) {
        Transaction transaction = null;
        try {
            Session session = entityManager.unwrap(Session.class);
            transaction = session.beginTransaction();

            session.delete(follow);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public Follow getFollow(Long followerId, Long followeeId) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Follow where folowerId = :followerId and foloweeId =: followeeId", Follow.class)
                .setParameter("followerId", followerId)
                .setParameter("followeeId", followeeId)
                .uniqueResult();

    }

}
