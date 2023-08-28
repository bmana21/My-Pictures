package com.example.mypictures.controller;

import com.example.mypictures.entity.User;
import com.example.mypictures.repository.UserRepository;
import com.example.mypictures.validator.UserLoginValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LogInController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login/loginPage";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String usernameOrEmail, @RequestParam String password, Model model, HttpSession session) {
        List<String> errorList = UserLoginValidator.validateInput(usernameOrEmail, password);
        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "login/loginPage";
        }
        errorList = UserLoginValidator.validateUser(usernameOrEmail, password, userRepository);
        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "login/loginPage";
        }
        User user = userRepository.findByUsername(usernameOrEmail);
        if (user == null)
            user = userRepository.findByEmail(usernameOrEmail);
        session.setAttribute("user", user);
        return "home/homePage";
    }
}
