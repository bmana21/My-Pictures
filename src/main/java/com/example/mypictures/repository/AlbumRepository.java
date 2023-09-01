package com.example.mypictures.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.mypictures.entity.Album;
import com.example.mypictures.entity.User;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    Album findByAlbumId(Long albumId);

    Album findByUserAndName(User user, String name);

    List<Album> findByUser(User user);

    Album findByUserAndSaveName(User user, String saveName);
}
