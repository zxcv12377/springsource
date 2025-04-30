package com.example.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.board.BoardApplication;
import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.service.BoardService;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/board")
@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void getlist(Model model, PageRequestDTO pageRequestDTO) {
        log.info("List 요청");
        PageResultDTO<BoardDTO> list = boardService.getList(pageRequestDTO);
        model.addAttribute("result", list);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long bno, Model model, PageRequestDTO pageRequestDTO) {
        log.info("read 요청");
        BoardDTO dto = boardService.readBoard(bno);
        model.addAttribute("dto", dto);
    }

}
