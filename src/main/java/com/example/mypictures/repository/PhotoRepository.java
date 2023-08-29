package com.example.mypictures.repository;

import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.User;
import org.springframework.data.repository.CrudRepository;
import com.example.mypictures.entity.Photo;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
    Photo findByPhotoId(Long photoId);

    List<Photo> findByAlbum(Album album);

    List<Photo> findByUser(User user);

}
