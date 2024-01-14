package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controllers.responseClasses.UserResponse;
import org.example.data.entity.User;
import org.example.exceptions.DataChangeException;
import org.example.repository.UserRepository;
import org.example.validation.ValidateUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service(value = "userService")
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ValidateUser validateUser;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

    /**
     * Converts a User entity to a UserResponse DTO.
     *
     * @param user User entity to be converted.
     * @return UserResponse DTO representing the user.
     */
    public UserResponse toUserDTO(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        return dto;
    }

    /**
     * Validates user credentials and performs login.
     *
     * @param userName User's username.
     * @param password User's password.
     * @throws IllegalAccessException If login fails due to invalid credentials.
     */
    public void logIn(String userName, String password) throws IllegalAccessException {
        User user = userRepository.getUserByUsername(userName);
        if (user == null || !cryptPasswordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalAccessException("email or password are invalid!");
        }
    }

    /**
     * Registers a new user, performing validation checks.
     *
     * @param user User entity representing the new user.
     * @throws ValidationException If user registration fails due to validation issues or duplicate data.
     */
    public void registerUser(User user) throws ValidationException {
        try {
            validateUser.validateUser(user);
            if (userRepository.getUserByUsername(user.getUsername()) != null) {
                throw new ValidationException("userName is already taken!");
            }
            if (userRepository.getUserByEmail(user.getEmail()) != null) {
                throw new ValidationException("email is already taken!");
            }
            String password = cryptPasswordEncoder.encode(user.getPasswordHash());
            user.setPasswordHash(password);
            userRepository.saveUser(user);
        } catch (Exception e) {
            throw new ValidationException(e);
        }

    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId ID of the user to retrieve.
     * @return User entity representing the user with the specified ID.
     */
    public User getUserById(Long userId){
        return userRepository.getUserById(userId);
    }
    /**
     * Retrieves a user by their username.
     *
     * @param username Username of the user to retrieve.
     * @return User entity representing the user with the specified username.
     */
    public User getUserByUserName(String username) {
        return userRepository.getUserByUsername(username);
    }

    /**
     * Load user details by username for Spring Security.
     *
     * @param username Username of the user to load.
     * @return UserDetails object representing the user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.getUserByUsername(username);
    }

    /**
     * Retrieves a UserResponse DTO by username, throwing an exception if the user is not found.
     *
     * @param username Username of the user to retrieve.
     * @return UserResponse DTO representing the user with the specified username.
     * @throws DataChangeException If the user with the specified username is not found.
     */
    public UserResponse getUserDTOByUsername(String username) throws DataChangeException {
        User user = userRepository.getUserByUsername(username);
        if(user == null){
            throw new DataChangeException("There is no user with this username!");
        }
        return toUserDTO(user);
    }

    /**
     * Retrieves a UserResponse DTO by ID, throwing an exception if the user is not found.
     *
     * @param id ID of the user to retrieve.
     * @return UserResponse DTO representing the user with the specified ID.
     * @throws DataChangeException If the user with the specified ID is not found.
     */
    public UserResponse getUserDTOById(Long id) throws DataChangeException {
        User user = userRepository.getUserById(id);
        if(user == null){
            throw new DataChangeException("There is no user with this username!");
        }
        return toUserDTO(user);
    }

    /**
     * Retrieves a list of UserResponse DTOs representing users that the specified user is following.
     *
     * @param id ID of the user whose following list is to be retrieved.
     * @return List of UserResponse DTOs representing users being followed by the specified user.
     */
    public List<UserResponse> getFollowingById(Long id){
        List<User> users = userRepository.getFollowing(id);
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of UserResponse DTOs representing users that the user with the specified username is following.
     *
     * @param username Username of the user whose following list is to be retrieved.
     * @return List of UserResponse DTOs representing users being followed by the specified user.
     */
    public List<UserResponse> getFollowingByUsername(String username){
        User u = getUserByUserName(username);
        List<User> users = userRepository.getFollowing(u.getId());
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }
    /**
     * Retrieves a list of UserResponse DTOs representing users that are following the specified user.
     *
     * @param id ID of the user whose followers list is to be retrieved.
     * @return List of UserResponse DTOs representing users following the specified user.
     */
    public List<UserResponse> getFollowersById(Long id){
        List<User> users = userRepository.getFollowers(id);
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of UserResponse DTOs representing users that are following the user with the specified username.
     *
     * @param username Username of the user whose followers list is to be retrieved.
     * @return List of UserResponse DTOs representing users following the specified user.
     */
    public List<UserResponse> getFollowersByUsername(String username){
        User u = getUserByUserName(username);
        List<User> users = userRepository.getFollowers(u.getId());
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

}
