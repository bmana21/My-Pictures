package com.example.mypictures.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {
    @GetMapping("/home")
    public String homePage(){
        return "home/homePage";
    }
}
