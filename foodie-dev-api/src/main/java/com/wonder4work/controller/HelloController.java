package com.wonder4work.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 1.0.0 2020/3/23
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public Object hello(){
        return "hello world";
    }

}
