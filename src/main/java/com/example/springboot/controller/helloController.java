package com.example.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "hello word~";
    }
}
