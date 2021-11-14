package com.rrain.jwttokenauth.controller;

import com.rrain.jwttokenauth.entity.AuthRequest;
import com.rrain.jwttokenauth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private AuthenticationManager authManager;



    @GetMapping("/spring/hello")
    private String hello(){
       return "hello spring!";
    }


    @PostMapping("/api/v1.0/login")
    private ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );
            String token = jwtUtils.generateToken(authRequest.username());
            return ResponseEntity.ok(token);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
