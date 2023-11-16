package org.example.validation;

import lombok.AllArgsConstructor;
import org.example.data.entity.User;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@AllArgsConstructor
@Component
public class ValidateUser {
//    private boolean isValidEmail(String email) {
//        String emailRegex = " ^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$;";
//        Pattern pattern = Pattern.compile(emailRegex);
//        return pattern.matcher(email).matches();
//    }
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    public void validateUser(User user) throws Exception {
//        if(!isValidEmail(user.getEmail())){
//            throw new Exception("email is not valid!");
//        }
        if(!isValidPassword(user.getPasswordHash())){
            throw new Exception("Password must contain at least 1 uppercase letter and at least one number, and has to be at least 8 characters!");
        }
    }
}
