package com.example.mypictures.controller;

import com.example.mypictures.security.UserSecurity;
import com.example.mypictures.validator.UserRegistrationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.mypictures.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.mypictures.entity.User;

import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "registration/registrationPage";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String surname, @RequestParam String phoneNumber, Model model) {
        List<String> errorList = UserRegistrationValidator.validateInput(username, email, password, firstName, surname, phoneNumber);
        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "registration/registrationPage";
        }
        errorList = UserRegistrationValidator.validateUser(username, email, phoneNumber, userRepository);
        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "registration/registrationPage";
        }
        String passwordHash = UserSecurity.hashPassword(password);
        User user = new User(username, passwordHash, email, firstName, surname, phoneNumber);
        userRepository.save(user);
        return "login/loginPage";
    }
}
