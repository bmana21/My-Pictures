package com.example.mypictures.entity;

import jakarta.persistence.*;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long albumId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String name;

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    private String saveName;

    public String getAlbumToken() {
        return albumToken;
    }

    public void setAlbumToken(String albumToken) {
        this.albumToken = albumToken;
    }

    private String albumToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    protected Album() {
    }

    public Album(User user, String name, String saveName, String albumToken) {
        this.user = user;
        this.name = name;
        this.saveName = saveName;
        this.albumToken = albumToken;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
