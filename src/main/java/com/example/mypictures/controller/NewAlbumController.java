package com.example.mypictures.controller;

import com.example.mypictures.constant.AlbumConstants;
import com.example.mypictures.constant.GoogleCloudConstants;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.Photo;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.AlbumRepository;
import com.example.mypictures.repository.PhotoRepository;
import com.example.mypictures.repository.UserRepository;
import com.example.mypictures.service.GoogleCloudService;
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
    private GoogleCloudService googleCloudService;

    @GetMapping("/newalbum")
    public String newAlbumPage(HttpSession session) {
        if (session.getAttribute("user") == null)
            return "login/loginPage";
        else return "album/newAlbumPage";
    }

    @PostMapping("/newalbum")
    public String addNewAlbum(@RequestParam String name, @RequestParam("albumCover") MultipartFile albumCover, @RequestParam("photos") MultipartFile[] photos, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login/loginPage";
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
        if (albumCover == null || albumCover.isEmpty())
            album = new Album(user, name, AlbumConstants.DEFAULT_COVER_LOCATION);
        else {
            String fileName = AlbumConstants.COVER_PREFIX + user.getUsername() + GoogleCloudConstants.SPLIT + user.getPasswordHash() + GoogleCloudConstants.SPLIT + name + GoogleCloudConstants.SPLIT + albumCover.getOriginalFilename();
            googleCloudService.uploadPhoto(fileName, albumCover);
            album = new Album(user, name, fileName);
        }
        albumRepository.save(album);
        if (!(photos == null || photos.length == 0)) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String fileName = AlbumConstants.PHOTO_PREFIX + user.getUsername() + GoogleCloudConstants.SPLIT + user.getPasswordHash() + GoogleCloudConstants.SPLIT + name + GoogleCloudConstants.SPLIT + photo.getOriginalFilename();
                    googleCloudService.uploadPhoto(fileName, photo);
                    Photo photo1 = new Photo(user, album, photo.getOriginalFilename(), fileName);
                    photoRepository.save(photo1);
                }
            }
        }
        return "redirect:home";
    }

}
