package com.example.test.dto.user;

import com.example.test.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
}
