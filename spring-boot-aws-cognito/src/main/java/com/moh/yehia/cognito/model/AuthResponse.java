package com.moh.yehia.cognito.model;

public record AuthResponse(String accessToken, int expiresIn, String tokenType, String refreshToken, String idToken) {
}
