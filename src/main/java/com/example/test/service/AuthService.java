package com.example.test.service;

import com.example.test.dto.UserRegisterRequest;
import com.example.test.dto.auth.AuthLoginRequest;
import com.example.test.dto.auth.AuthLoginResponse;
import com.example.test.entity.User;

public interface AuthService {
    String register(UserRegisterRequest userRegisterRequest);

    AuthLoginResponse login(AuthLoginRequest authLoginRequest);
    User getUsernameFromToken(String token);
}
