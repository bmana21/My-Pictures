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
import com.example.mypictures.service.GoogleCloudService;
import com.example.mypictures.validator.PhotoValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AddPhotoController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private GoogleCloudService googleCloudService;
    @Autowired
    private RememberMeCookie rememberMeCookie;

    @RequestMapping("/addphoto")
    public String addPhoto(@RequestParam Long albumId, @RequestParam("photos") MultipartFile[] photos, HttpSession session, HttpServletRequest request) throws IOException {
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
        if (!(photos == null || photos.length == 0)) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String originalFileName = PhotoValidator.getOriginalFileName(photo.getOriginalFilename(), user, album, albumRepository, photoRepository, false);
                    String fileName = AlbumConstants.PHOTO_PREFIX + user.getUsername() + GoogleCloudConstants.SPLIT + album.getAlbumToken() + GoogleCloudConstants.SPLIT + album.getName() + GoogleCloudConstants.SPLIT + originalFileName;
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
        return "redirect:/album?albumId=" + albumId;
    }
}
