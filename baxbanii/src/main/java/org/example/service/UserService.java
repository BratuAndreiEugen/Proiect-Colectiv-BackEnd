package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.UserDTO;
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

    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        return dto;
    }

    public void logIn(String userName, String password) throws IllegalAccessException {
        User user = userRepository.getUserByUsername(userName);
        if (user == null || !cryptPasswordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalAccessException("email or password are invalid!");
        }
    }

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

    public User getUserById(Long userId){
        return userRepository.getUserById(userId);
    }
    public User getUserByUserName(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.getUserByUsername(username);
    }

    public UserDTO getUserDTOByUsername(String username) throws DataChangeException {
        User user = userRepository.getUserByUsername(username);
        if(user == null){
            throw new DataChangeException("There is no user with this username!");
        }
        return toUserDTO(user);
    }


    public UserDTO getUserDTOById(Long id) throws DataChangeException {
        User user = userRepository.getUserById(id);
        if(user == null){
            throw new DataChangeException("There is no user with this username!");
        }
        return toUserDTO(user);
    }


    public List<UserDTO> getFollowingById(Long id){
        List<User> users = userRepository.getFollowing(id);
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getFollowingByUsername(String username){
        User u = getUserByUserName(username);
        List<User> users = userRepository.getFollowing(u.getId());
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getFollowersById(Long id){
        List<User> users = userRepository.getFollowers(id);
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getFollowersByUsername(String username){
        User u = getUserByUserName(username);
        List<User> users = userRepository.getFollowers(u.getId());
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

}
