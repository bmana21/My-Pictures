package com.example.mypictures.controller;

import com.example.mypictures.constant.AlbumConstants;
import com.example.mypictures.constant.GoogleCloudConstants;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.Photo;
import com.example.mypictures.entity.User;
import com.example.mypictures.repository.AlbumRepository;
import com.example.mypictures.repository.PhotoRepository;
import com.example.mypictures.service.GoogleCloudService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AddPhotoController {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private GoogleCloudService googleCloudService;

    @RequestMapping("/addphoto")
    public String addPhoto(@RequestParam Long albumId, @RequestParam("photos") MultipartFile[] photos, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login/loginPage";
        Album album = albumRepository.findByAlbumId(albumId);
        if (album == null)
            return "redirect:/home";
        if (!(photos == null || photos.length == 0)) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String fileName = AlbumConstants.PHOTO_PREFIX + user.getUsername() + GoogleCloudConstants.SPLIT + user.getPasswordHash() + GoogleCloudConstants.SPLIT + album.getName() + GoogleCloudConstants.SPLIT + photo.getOriginalFilename();
                    googleCloudService.uploadPhoto(fileName, photo);
                    Photo photo1 = new Photo(user, album, photo.getOriginalFilename(), fileName);
                    photoRepository.save(photo1);
                }
            }
        }
        return "redirect:/album?albumId=" + albumId;
    }
}
