package com.example.test.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginResponse {
    private Long id;
    private String username;
    private String token;
    private String name;
}
