package com.example.novels.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novels.dto.NovelDTO;
import com.example.novels.dto.PageRequestDTO;
import com.example.novels.dto.PageResultDTO;
import com.example.novels.service.NovelService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/api/books")
@RestController
@RequiredArgsConstructor
@Log4j2
public class NovelRestController {
    private final NovelService novelService;

    // 도서 리스트
    @GetMapping("")
    public PageResultDTO<NovelDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("전체 도서 정보 {}", pageRequestDTO);

        return novelService.getList(pageRequestDTO);
    }

    // 도서 정보
    @GetMapping("/{id}")
    public NovelDTO getRead(@PathVariable Long id) {
        log.info("도서 정보 {}", id);

        return novelService.getRow(id);
    }

    // 도서 업데이트
    @PutMapping("/{id}")
    public Long putNovel(@RequestBody NovelDTO dto) {
        log.info("도서 업데이트 {}", dto);

        return novelService.avaUpdate(dto);
    }

    // 도서 입력
    @PostMapping("/add")
    public Long postNovel(@RequestBody NovelDTO dto) {
        log.info("도서 추가 {}", dto);

        return novelService.novelInsert(dto);
    }

    // 도서 삭제
    @Transactional
    @DeleteMapping("/{id}")
    public void removeNovel(@PathVariable Long id) {
        novelService.novelRemove(id);
    }
}
