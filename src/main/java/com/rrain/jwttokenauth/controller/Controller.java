package com.rrain.jwttokenauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/spring/hello")
    private String hello(){
        return "hello spring!";
    }


}
