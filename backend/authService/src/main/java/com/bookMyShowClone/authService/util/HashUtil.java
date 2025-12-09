package com.bookMyShowClone.authService.util;

import java.security.MessageDigest;
import java.util.Base64;

public class HashUtil {

    public static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Unable to hash refresh token");
        }
    }
}
