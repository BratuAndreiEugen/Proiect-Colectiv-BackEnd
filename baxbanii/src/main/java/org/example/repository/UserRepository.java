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

    /**
     * Returns user by email / null (if not found)
     *
     * @param email
     * @return one username or null
     */
    public User getUserByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from User where email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    /**
     * Returns user by email and password / null (if not found)
     *
     * @param email
     * @param password
     * @return one username or null
     */
    public User getUserByEmailAndPassword(String email, String password) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from User where email = :email and passwordHash = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
    }

    public List<User> getFollowing(Long id){
        Session session = entityManager.unwrap(Session.class);
        List<User> followersList = session.createQuery(
                        "SELECT u FROM User u " +
                                "JOIN Follow f ON u.id = f.foloweeId " +
                                "WHERE f.folowerId = :id", User.class)
                .setParameter("id", id)
                .getResultList();

        return followersList != null ? followersList : Collections.emptyList();
    }

    public List<User> getFollowers(Long id){
        Session session = entityManager.unwrap(Session.class);
        List<User> followersList = session.createQuery(
                        "SELECT u FROM User u " +
                                "JOIN Follow f ON u.id = f.folowerId " +
                                "WHERE f.foloweeId = :id", User.class)
                .setParameter("id", id)
                .getResultList();

        return followersList != null ? followersList : Collections.emptyList();
    }

    /**
     * Saves a user to the database.
     *
     * @param user the user to be saved
     */
    public void saveUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        if (user.getId() == null) {
            session.save(user);
        } else {
            session.saveOrUpdate(user);
        }
    }
}