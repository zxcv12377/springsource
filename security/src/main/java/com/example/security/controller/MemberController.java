package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getlogin() {
        log.info("login 폼 요청");
    }

}
