package com.example.mypictures.controller;

import com.example.mypictures.constant.AlbumConstants;
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

import java.util.List;
import java.util.Objects;

@Controller
public class DeleteAlbumController {
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

    @RequestMapping("/deletealbum")
    @Transactional
    public String deleteAlbum(@RequestParam(name = "albumId") Long albumId, HttpSession session, HttpServletRequest request) throws Exception {
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

        List<Photo> photos = photoRepository.findByAlbum(album);
        for(Photo photo : photos){
            googleCloudService.deletePhoto(photo.getSaveName());
            photoRepository.deleteByPhotoId(photo.getPhotoId());
        }
        if(!Objects.equals(album.getSaveName(), AlbumConstants.DEFAULT_COVER_LOCATION))
            googleCloudService.deletePhoto(album.getSaveName());
        albumRepository.deleteByAlbumId(albumId);
        return "redirect:/home";
    }
}
