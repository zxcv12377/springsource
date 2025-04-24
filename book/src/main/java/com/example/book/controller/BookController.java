package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book.dto.BookDTO;

@Log4j2
@RequestMapping("/book")
@Controller
@RequiredArgsConstructor
public class BookController {
    @GetMapping("/list")
    public void getList() {
        log.info("book list 요청");

    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code) {
        log.info("book get 요청 {}", code);
    }

    @GetMapping("/modify")
    public void getModify(BookDTO dto) {
        log.info("book modify 요청 {}", dto);
    }

    @GetMapping("/remove")
    public void getRemove(Long code) {
        log.info("book remove 요청 {}", code);
    }

}
