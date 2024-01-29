package com.example.test.mapper;

import com.example.test.dto.user.UserResponse;
import com.example.test.entity.User;

import java.util.List;

public interface UserMapper {
    List<UserResponse> toDtoS(List<User> users);
}
