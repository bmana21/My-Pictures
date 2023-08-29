package com.example.mypictures.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogOutController {
    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}
