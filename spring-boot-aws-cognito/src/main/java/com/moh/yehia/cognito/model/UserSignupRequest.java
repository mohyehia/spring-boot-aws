package com.moh.yehia.cognito.model;

public record UserSignupRequest(String email, String username, String phone, String name, String password) {
}
