package com.example.board.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // private final

    @GetMapping("/admin")
    public void getadmin() {
        log.info("admin 폼 요청");
    }

    @GetMapping("/login")
    public void getlogin() {
        log.info("로그인 폼 요청");
    }

    @GetMapping("/register")
    public void getRegister() {
        log.info("회원가입 폼 요청");
    }

    @PostMapping("/register")
    public void postRegister() {

    }

    // 개발자 확인용
    @ResponseBody
    @GetMapping("/auth")
    public Authentication getaAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

}
