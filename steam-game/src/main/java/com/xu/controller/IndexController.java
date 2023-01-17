package com.xu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "Hello World";
    }


    @GetMapping("/error")
    public String error(){
        return "Hello Error";
    }


    @GetMapping("/successLogout")
    public String successLogout(){
        return "Hello Error";
    }
}
