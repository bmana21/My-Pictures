package com.example.mypictures.cookie;

import com.example.mypictures.entity.User;
import com.example.mypictures.constant.CookieConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class RememberMeCookie {
    public void setToken(String token, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.REMEMBER_ME_COOKIE_NAME)) {
                    cookie.setValue(token);
                    cookie.setMaxAge(CookieConstants.REMEMBER_ME_AGE);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        Cookie rememberMeCookie = new Cookie(CookieConstants.REMEMBER_ME_COOKIE_NAME, token);
        rememberMeCookie.setMaxAge(CookieConstants.REMEMBER_ME_AGE);
        rememberMeCookie.setPath("/");
        rememberMeCookie.setHttpOnly(true);
        response.addCookie(rememberMeCookie);
    }

    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.REMEMBER_ME_COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public void removeToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.REMEMBER_ME_COOKIE_NAME)) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }


}
