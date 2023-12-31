package com.example.mypictures.controller;

import com.example.mypictures.cookie.RememberMeCookie;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.Photo;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.AlbumRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.mypictures.repository.PhotoRepository;
import com.example.mypictures.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AlbumController {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RememberMeCookie rememberMeCookie;

    @RequestMapping("/album")
    public String album(@RequestParam Long albumId, Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = userRepository.findByRememberMeToken(rememberMeCookie.getToken(request));
            if (user == null)
                return "login/loginPage";
            session.setAttribute("user", user);
        }
        if (albumId == null)
            return "redirect:/home";
        Album album = albumRepository.findByAlbumIdAndUser(albumId, user);
        if (album == null || !album.getUser().getUserId().equals(user.getUserId()))
            return "redirect:/home";
        List<Photo> photos = photoRepository.findByAlbum(album);
        if (photos == null)
            photos = new ArrayList<>();
        List<Album> albums = albumRepository.findByUser(user);
        model.addAttribute("album", album);
        model.addAttribute("photos", photos);
        model.addAttribute("albums", albums);
        return "album/albumPage";
    }

}
