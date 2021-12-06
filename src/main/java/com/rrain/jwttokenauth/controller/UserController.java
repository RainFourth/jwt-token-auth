package com.rrain.jwttokenauth.controller;

import com.rrain.jwttokenauth.entity.AuthRequest;
import com.rrain.jwttokenauth.entity.User;
import com.rrain.jwttokenauth.repo.UserRepo;
import com.rrain.jwttokenauth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserController {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepo userRepo;



    @GetMapping("/spring/hello")
    private String hello(){
       return "hello spring!";
    }


    @PostMapping("/api/v1.0/login")
    private ResponseEntity<?> login(@RequestBody(required = false) AuthRequest authRequest){
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );
            String token = jwtUtils.generateToken(authRequest.username());
            return ResponseEntity.ok(token);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/api/v1.0/me")
    private ResponseEntity<?> me(Principal principal){
        try {
            User user = userRepo.findUserByUsername(principal.getName()).get();
            return ResponseEntity.ok(Map.of("Username: ", user.getUsername()));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
