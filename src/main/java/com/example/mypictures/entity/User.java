package com.example.mypictures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username, passwordHash, email;
    private String firstname, surname;
    private String phoneNumber;
    private String rememberMeToken;

    public String getRememberMeToken() {
        return rememberMeToken;
    }

    public void setRememberMeToken(String rememberMeToken) {
        this.rememberMeToken = rememberMeToken;
    }




    public User(String username, String passwordHash, String email, String firstname, String surname, String phoneNumber) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.firstname = firstname;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    protected User() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User: [" +
                "id: " + getUserId() +
                ", username: " + getUsername() +
                ", passwordHash: " + getPasswordHash() +
                ", email: " + getEmail() +
                ", firstname: " + getFirstname() +
                ", surname: " + getSurname() +
                ", phoneNumber: " + getPhoneNumber() +
                "];";
    }

}

