package com.example.test.mapper.impl;

import com.example.test.dto.user.UserResponse;
import com.example.test.entity.User;
import com.example.test.enums.Role;
import com.example.test.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public List<UserResponse> toDtoS(List<User> users) {
        List<UserResponse> response = new ArrayList<>();
        for(User user : users){
            if(!user.getRole().equals(Role.DIRECTOR)){
                response.add(toDto(user));
            }
        }
        return response;
    }

    public UserResponse toDto(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        return response;
    }
}
