package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.entity.User;
import org.example.repository.UserRepository;
import org.example.validation.ValidateUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.ValidationException;

@org.springframework.stereotype.Service(value = "userService")
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ValidateUser validateUser;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

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
}
