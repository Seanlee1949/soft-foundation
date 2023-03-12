package com.example.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lishuai
 * @since 2022/11/23
 */
@RestController
@RequestMapping(value = "/api/demo/boot")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!!!";
    }
}
