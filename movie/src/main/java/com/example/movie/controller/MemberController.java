package com.example.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.security.Principal;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.AuthMemberDTO;
import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.MemberRole;
import com.example.movie.service.CustomMemberDetailsService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final CustomMemberDetailsService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/leave")
    public void getLeave() {
        log.info("회원탈퇴 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/leave")
    public String postLeave(MemberDTO dto, BindingResult result, HttpSession session, Model model) {
        log.info("회원탈퇴 요청 {}", dto);
        if (result.hasErrors()) {
            return "/member/leave";
        }
        try {
            service.leaveMember(dto);
            session.invalidate();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "member/leave";
        }

        return "redirect:/movie/list";

    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 폼 요청");
    }

    @GetMapping("/register")
    public void getRegister(MemberDTO dto) {
        log.info("회원가입 폼 요청");
    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDTO dto, BindingResult result, Model model) {
        log.info("회원가입 요청 {}", dto);

        if (result.hasErrors()) {
            return "/member/register";
        }
        dto.setMemberRole(MemberRole.MEMBER);

        try {

            service.register(dto);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/member/register";
        }
        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile() {
        log.info("프로필 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String postNickname(MemberDTO dto, Principal principal) {
        log.info("닉네임 수정 요청 {} {}", dto, principal);

        // 이메일
        dto.setEmail(principal.getName());
        service.updateNickname(dto);
        // securityContext 비밀번호 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();
        authMemberDTO.getMemberDTO().setNickname(dto.getNickname());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postPassword(PasswordDTO passwordDTO, Model model, HttpSession session) {
        log.info("비밀번호 수정 요청 {}", passwordDTO);
        try {
            service.updatePassword(passwordDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/member/edit";
        }
        session.invalidate();
        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public void getEdit() {
        log.info("프로필 수정 폼 요청");
    }

    // 개발자 확인용
    // @ResponseBody
    // @GetMapping("/auth")
    // public Authentication getaAuthentication() {
    // SecurityContext context = SecurityContextHolder.getContext();
    // Authentication authentication = context.getAuthentication();
    // return authentication;
    // }

}
