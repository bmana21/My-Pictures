package com.example.mypictures.controller;

import com.example.mypictures.constant.AlbumConstants;
import com.example.mypictures.constant.GoogleCloudConstants;
import com.example.mypictures.cookie.RememberMeCookie;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.Photo;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.AlbumRepository;
import com.example.mypictures.repository.PhotoRepository;
import com.example.mypictures.repository.UserRepository;
import com.example.mypictures.security.TokenGenerator;
import com.example.mypictures.service.GoogleCloudService;
import com.example.mypictures.validator.PhotoValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NewAlbumController {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoogleCloudService googleCloudService;
    @Autowired
    private RememberMeCookie rememberMeCookie;

    @GetMapping("/newalbum")
    public String newAlbumPage(HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("user") == null) {
            if (userRepository.findByRememberMeToken(rememberMeCookie.getToken(request)) == null)
                return "login/loginPage";
        }
        return "album/newAlbumPage";
    }

    @PostMapping("/newalbum")
    public String addNewAlbum(@RequestParam String name, @RequestParam("albumCover") MultipartFile albumCover, @RequestParam("photos") MultipartFile[] photos, HttpSession session, Model model, HttpServletRequest request) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = userRepository.findByRememberMeToken(rememberMeCookie.getToken(request));
            if (user == null)
                return "login/loginPage";
            session.setAttribute("user", user);
        }
        List<String> errorList = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            errorList.add("Enter Album Name");
            model.addAttribute("errors", errorList);
            return "album/newAlbumPage";
        }

        if (albumRepository.findByUserAndName(user, name) != null) {
            errorList.add("You Already Have Album With That Name");
            model.addAttribute("errors", errorList);
            return "album/newAlbumPage";
        }

        Album album;
        name = PhotoValidator.validateName(name);
        String token = TokenGenerator.generateToken(name);
        if (albumCover == null || albumCover.isEmpty())
            album = new Album(user, name, AlbumConstants.DEFAULT_COVER_LOCATION, token);
        else {
            String originalFileName = PhotoValidator.getOriginalFileName(albumCover.getOriginalFilename(), user, null, albumRepository, photoRepository, true);
            String fileName = AlbumConstants.COVER_PREFIX + user.getUsername() + GoogleCloudConstants.SPLIT + token + GoogleCloudConstants.SPLIT + name + GoogleCloudConstants.SPLIT + originalFileName;
            googleCloudService.uploadPhoto(fileName, albumCover);
            album = new Album(user, name, fileName, token);
        }
        albumRepository.save(album);
        if (!(photos == null || photos.length == 0)) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String originalFileName = PhotoValidator.getOriginalFileName(photo.getOriginalFilename(), user, album, albumRepository, photoRepository, false);
                    String fileName = AlbumConstants.PHOTO_PREFIX + user.getUsername() + GoogleCloudConstants.SPLIT + token + GoogleCloudConstants.SPLIT + name + GoogleCloudConstants.SPLIT + originalFileName;
                    if (PhotoValidator.photoIsLarge(photo)) {
                        fileName = PhotoValidator.changeToJPNG(fileName);
                        originalFileName = PhotoValidator.changeToJPNG(originalFileName);
                        googleCloudService.uploadPhotoBytes(fileName, PhotoValidator.scalePhoto(photo));
                    } else googleCloudService.uploadPhoto(fileName, photo);
                    Photo photo1 = new Photo(user, album, originalFileName, fileName);
                    photoRepository.save(photo1);
                }
            }
        }
        return "redirect:home";
    }

}
