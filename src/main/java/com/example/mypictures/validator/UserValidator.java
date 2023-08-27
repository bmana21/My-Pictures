package com.example.mypictures.validator;

import com.example.mypictures.constant.UserConstants;
import com.example.mypictures.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static List<String> validateInput(String username, String email, String password, String firstName, String surname, String phoneNumber) {
        List<String> errorList = new ArrayList<>();
        if (username == null || username.length() < UserConstants.MIN_USERNAME_LENGTH)
            errorList.add("Enter Username With Length More Than " + UserConstants.MIN_USERNAME_LENGTH + "!");
        if (email == null || !email.contains("@"))
            errorList.add("Enter Valid Email");
        if (password == null || password.length() < UserConstants.MIN_PASSWORD_LENGTH)
            errorList.add("Enter Password With Length More Than " + UserConstants.MIN_PASSWORD_LENGTH + "!");
        if (firstName == null || firstName.isEmpty())
            errorList.add("Enter your Firstname!");
        if (surname == null || surname.isEmpty())
            errorList.add("Enter your Surname!");
        if (phoneNumber == null || phoneNumber.length() < UserConstants.MIN_PHONE_NUMBER_LENGTH)
            errorList.add("Enter valid phone number!");
        return errorList;
    }

    public static List<String> validateUser(String username, String email, String phoneNumber, UserRepository userRepository) {
        List<String> errorList = new ArrayList<>();
        if (userRepository.findByUsername(username) != null)
            errorList.add("Username already in use!");
        if (userRepository.findByEmail(email) != null)
            errorList.add("Email already in use!");
        if (userRepository.findByPhoneNumber(phoneNumber) != null)
            errorList.add("Phone Number already in use!");
        return errorList;

    }
}
