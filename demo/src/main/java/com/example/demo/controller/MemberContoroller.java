package com.example.demo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberContoroller {
    // 회원가입 : /member/register
    // 로그인 : /member/login
    // 로그아웃 : /member/logout
    // 비밀번호 변경 : /member/change
    @GetMapping("/register")
    public void getRegister(@ModelAttribute("mDto") MemberDTO memberDTO) {
        log.info("회원가입");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 페이지 요청");
        // template 찾기
    }

    @PostMapping("/login")
    // public void postLogin(String userid, String password) {
    // public void postLogin(LoginDTO loginDTO) {
    public void postLogin(HttpServletRequest request) {
        // HttpServletRequest request : 사용자의 정보 및 서버 쪽 정보 추출
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String remote = request.getRemoteAddr();
        String local = request.getLocalAddr();

        log.info("로그인 요청 {} {} {} {} {}", userid, password, email, name, age);
        log.info("클라이언트 정보 {} {} ", remote, local);
        // template 찾기
    }

    @GetMapping("/logout")
    public void getLogOut() {
        log.info("로그아웃");
    }

    @GetMapping("/change")
    public void getChange() {
        log.info("비밀번호 변경");
    }

    @PostMapping("/register") // @Valid 와 BindingResult는 반드시 이어서 쓰기
    public String postRegister(@ModelAttribute("mDto") @Valid MemberDTO memberDTO, BindingResult result,
            RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", memberDTO);

        // 유효성 검사를 통과하지 못한다면 원래 입력 페이지로 돌아가기

        if (result.hasErrors()) {
            return "/member/register";
        }

        // 로그인 페이지로 이동
        // redirect 방식으로 가면서 값을 보내고 싶다면
        rttr.addAttribute("userid", memberDTO.getUserid());
        // 주소에 key-value 값을 넣어주고 싶으면 사용↑
        rttr.addFlashAttribute("password", memberDTO.getPassword());
        rttr.addAttribute("email", memberDTO.getEmail());
        rttr.addAttribute("name", memberDTO.getName());
        rttr.addAttribute("age", memberDTO.getAge());
        return "redirect:/member/login";

    }

}
