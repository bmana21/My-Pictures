package com.example.mypictures.security;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateToken(String name) {
        String token;
        do {
            byte[] randomBytes = new byte[32];
            secureRandom.nextBytes(randomBytes);
            token = name + "_" + base64Encoder.encodeToString(randomBytes);
        } while (token.contains("?"));

        return token;
    }
}