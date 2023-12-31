package com.example.mypictures.controller;

import com.example.mypictures.cookie.RememberMeCookie;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.Photo;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.AlbumRepository;
import com.example.mypictures.repository.PhotoRepository;
import com.example.mypictures.repository.UserRepository;
import com.example.mypictures.service.GoogleCloudService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeletePhotoController {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private GoogleCloudService googleCloudService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RememberMeCookie rememberMeCookie;

    @RequestMapping("/deletephoto")
    @Transactional
    public String deletePhoto(@RequestParam(name = "albumId") Long albumId, @RequestParam(name = "photoId") Long photoId, HttpSession session, HttpServletRequest request) throws Exception {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = userRepository.findByRememberMeToken(rememberMeCookie.getToken(request));
            if (user == null)
                return "login/loginPage";
            session.setAttribute("user", user);
        }
        Album album = albumRepository.findByAlbumIdAndUser(albumId, user);
        if (album == null)
            return "redirect:/home";
        Photo photo = photoRepository.findByAlbumAndPhotoId(album, photoId);
        if (photo == null)
            return "redirect:/home";
        googleCloudService.deletePhoto(photo.getSaveName());
        photoRepository.deleteByPhotoId(photoId);
        return "redirect:/album?albumId=" + album.getAlbumId();
    }
}
