package com.example.board.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 시 어디로 이동할 것인가?
        // 기본은 로그인 작업 이전의 페이지로 이동함

        AuthMemberDTO clubAuthMemberDTO = (AuthMemberDTO) authentication.getPrincipal();
        log.info("CustomLoginSuccessHandler {}", clubAuthMemberDTO);

        // ROLE에 따라 가는 경로를 다르게 한다면

        // ROLE 확인
        List<String> roleName = new ArrayList<>();
        clubAuthMemberDTO.getAuthorities().forEach(auth -> {
            roleName.add(auth.getAuthority());
        });

        log.info("roleName {}", roleName);

        if (roleName.contains("ROLE_ADMIN")) {
            response.sendRedirect("/member/admin");
            return;
        }
        if (roleName.contains("ROLE_USER") || roleName.contains("ROLE_MANAGER")) {
            response.sendRedirect("/board/list");
            return;
        }
        response.sendRedirect("/");
    }

}
