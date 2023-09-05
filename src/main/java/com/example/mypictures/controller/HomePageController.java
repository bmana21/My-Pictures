package com.example.mypictures.controller;

import com.example.mypictures.cookie.RememberMeCookie;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.AlbumRepository;
import com.example.mypictures.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RememberMeCookie rememberMeCookie;

    @RequestMapping("/home")
    public String homePage(HttpSession session, Model model, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            user = (User) model.getAttribute("userRedirect");
        if (user == null)
            user = userRepository.findByRememberMeToken(rememberMeCookie.getToken(request));
        if (user != null) {
            session.setAttribute("user", user);
            List<Album> albums = albumRepository.findByUser(user);
            if (albums == null)
                albums = new ArrayList<>();
            model.addAttribute("albums", albums);
        }
        return "home/homePage";
    }
}
