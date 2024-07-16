package com.home.code.ebay.util;

import java.util.Base64;


public class TokenUtil {

    public static String generateToken(String userId, String role) {
        String token = userId+","+role;
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    public static String[] parseToken(String token) {
        String decodeString = new String(Base64.getDecoder().decode(token));
        String[] info = decodeString.split(",");
        if (info.length != 2) {
            throw new RuntimeException("token is invalid");
        }
        return info;
    }

}