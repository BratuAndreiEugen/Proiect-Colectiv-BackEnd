package org.example.repository;

import org.example.data.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private EntityManager entityManager;

    /**
     * Returns all users from DB
     *
     * @return all users
     */
    public List<User> getAllUsers() {
        Session session = entityManager.unwrap(Session.class);
        List<User> users = session.createQuery("from User", User.class).getResultList();
        return users != null ? users : Collections.emptyList();
    }

    /**
     * Returns user by username / null (if not found)
     *
     * @param username
     * @return one username or null
     */
    public User getUserByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from User where username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    /**
     * Returns user by id / null (if not found)
     *
     * @param id
     * @return one user or null
     */
    public User getUserById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from User where id = :id", User.class)
                .setParameter("id", id)
                .uniqueResult();
    }
}