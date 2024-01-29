package com.example.test.controller;

import com.example.test.dto.user.UserResponse;
import com.example.test.entity.User;
import com.example.test.enums.Role;
import com.example.test.exception.BadRequestException;
import com.example.test.mapper.UserMapper;
import com.example.test.repository.UserRepository;
import com.example.test.service.AuthService;
import com.example.test.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public List<UserResponse> all(@RequestHeader String token){
        if(authService.getUserFromToken(token).getRole().equals(Role.CUSTOMER)){
            throw new BadRequestException("You have no permission.");
        }
        List<User> users = userRepository.findAll();
        return userMapper.toDtoS(users);
    }
}
