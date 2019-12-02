package com.test.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class demo {

    @GetMapping("/hello")
    public String sayHello(){

        return "HELLO WORLD";
    }

}
