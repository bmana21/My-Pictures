package com.example.mypictures.controller;

import com.example.mypictures.cookie.RememberMeCookie;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.UserRepository;
import com.example.mypictures.security.TokenGenerator;
import com.example.mypictures.validator.UserLoginValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LogInController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RememberMeCookie rememberMeCookie;


    @GetMapping("/login")
    public String showLoginPage(HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            user = userRepository.findByRememberMeToken(rememberMeCookie.getToken(request));
        if (user != null)
            return "redirect:/home";
        return "login/loginPage";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String usernameOrEmail, @RequestParam String password, @RequestParam(name = "rememberMe", required = false) boolean rememberMe, Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            user = userRepository.findByRememberMeToken(rememberMeCookie.getToken(request));
        if (user != null)
            return "redirect:/home";
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
        user = userRepository.findByUsername(usernameOrEmail);
        if (user == null)
            user = userRepository.findByEmail(usernameOrEmail);
        session.setAttribute("user", user);
        redirectAttributes.addFlashAttribute("userRedirect", user);
        if (rememberMe) {
            String token = TokenGenerator.generateToken(user.getUsername());
            user.setRememberMeToken(token);
            userRepository.save(user);
            rememberMeCookie.setToken(token, request, response);
        }
        return "redirect:/home";
    }
}
