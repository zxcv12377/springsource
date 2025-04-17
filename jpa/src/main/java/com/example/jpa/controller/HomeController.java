package com.example.jpa.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller

public class HomeController {
    @GetMapping("/")
    public String getHome() {
        log.info("home 페이지 요청");
        return "home";
    }

    @GetMapping("/main")
    public void getMain() {
        log.info("main 페이지 요청");
    }
}
