package com.example.movie.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.PageRequestDTO;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/movie/list";
    }

    @GetMapping("/error")
    public String getError() {
        return "/except/url404";
    }

}
