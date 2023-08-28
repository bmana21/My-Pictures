package com.example.mypictures.validator;

import com.example.mypictures.constant.UserConstants;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.UserRepository;
import com.example.mypictures.security.UserSecurity;

import java.util.ArrayList;
import java.util.List;

public class UserLoginValidator {

    public static List<String> validateInput(String usernameOrEmail, String password) {
        List<String> errorList = new ArrayList<>();
        if (usernameOrEmail == null || usernameOrEmail.isEmpty())
            errorList.add("Enter Your Username Or Email");
        if (password == null || password.isEmpty())
            errorList.add("Enter Your Password");
        return errorList;
    }

    public static List<String> validateUser(String usernameOrEmail, String password, UserRepository userRepository) {
        List<String> errorList = new ArrayList<>();
        User userByUsername = userRepository.findByUsername(usernameOrEmail);
        User userByEmail = userRepository.findByEmail(usernameOrEmail);
        if (userByUsername == null && userByEmail == null) {
            errorList.add("Username Or Email is Incorrect");
            return errorList;
        }
        String passwordHash = UserSecurity.hashPassword(password);
        User user = userByEmail;
        if (user == null)
            user = userByUsername;
        if (!user.getPasswordHash().equals(passwordHash))
            errorList.add("Password is Incorrect");
        return errorList;
    }
}
