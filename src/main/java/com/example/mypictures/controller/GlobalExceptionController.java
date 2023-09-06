package com.example.mypictures.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "An error occurred: " + ex.getMessage();
        model.addAttribute("errorMessage", errorMessage);
        return "error/errorPage";
    }
}