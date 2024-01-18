package com.example.test.service.impl;

import com.example.test.config.JwtService;
import com.example.test.dto.UserRegisterRequest;
import com.example.test.dto.auth.AuthLoginRequest;
import com.example.test.dto.auth.AuthLoginResponse;
import com.example.test.entity.Customer;
import com.example.test.entity.Director;
import com.example.test.entity.User;
import com.example.test.enums.Role;
import com.example.test.exception.BadRequestException;
import com.example.test.repository.DirectorRepository;
import com.example.test.repository.UserRepository;
import com.example.test.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.net.Proxy;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private DirectorRepository directorRepository;
    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        if (userRepository.findByUsername(userRegisterRequest.getUsername()).isPresent())
            throw new BadCredentialsException("user with email: "+userRegisterRequest.getUsername()+" is already exist!");

        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        if(!containsType(userRegisterRequest.getRole())){
            throw new BadRequestException("Lol");
        }
        user.setRole(Role.valueOf(userRegisterRequest.getRole()));
        user.setPassword(encoder.encode(userRegisterRequest.getPassword()));
        if(user.getRole().equals(Role.CUSTOMER)) {
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setName(userRegisterRequest.getName());
            user.setCustomer(customer);
            //customerRepository.save(customer);
        } else if(user.getRole().equals(Role.DIRECTOR)) {
            Director director = new Director();
            director.setName(userRegisterRequest.getName());
            user.setDirector(director);
            directorRepository.save(director);
        }
        userRepository.save(user);
    }

    @Override
    public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
        Optional<User> user = userRepository.findByUsername(authLoginRequest.getUsername());
        if (user.isEmpty())
            throw new BadCredentialsException("user not found");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authLoginRequest.getUsername(),authLoginRequest.getPassword()));

        }catch (org.springframework.security.authentication.BadCredentialsException e){
            throw new BadCredentialsException("user not found");
        }


        return convertToResponse(user);
    }

    private AuthLoginResponse convertToResponse(Optional<User> user) {
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setUsername(user.get().getUsername());
        authLoginResponse.setId(user.get().getId());
        if (user.get().getRole().equals(Role.CUSTOMER))
            authLoginResponse.setName(user.get().getCustomer().getName());
        Map<String, Object> extraClaims = new HashMap<>();

        String token = jwtService.generateToken(extraClaims, user.get());
        authLoginResponse.setToken(token);

        return authLoginResponse;
    }

    @Override
    public User getUsernameFromToken(String token){

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) jsonParser.parse(decoder.decode(chunks[1]));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return userRepository.findByUsername(String.valueOf(object.get("sub"))).orElseThrow(() -> new RuntimeException("user can be null"));
    }
    private boolean containsType(String type) {
        for (Role role:Role.values()){
            if (role.name().equalsIgnoreCase(type))
                return true;
        }
        return false;
    }
}
