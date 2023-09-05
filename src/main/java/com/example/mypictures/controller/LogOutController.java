package com.example.mypictures.controller;

import com.example.mypictures.cookie.RememberMeCookie;
import com.example.mypictures.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogOutController {
    @Autowired
    private RememberMeCookie rememberMeCookie;
    @GetMapping("/logout")
    public String logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        rememberMeCookie.removeToken(request, response);
        session.invalidate();
        return "redirect:/home";
    }
}
