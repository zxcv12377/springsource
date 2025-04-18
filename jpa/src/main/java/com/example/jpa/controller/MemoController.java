package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.service.MemoService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {
    // 서비스 메소드 호출
    // 데이터가 전송 된다면 전송된 데이터를 모델에 담기
    private final MemoService memoService;

    // 주소 설계
    // 전체 memo 조회 : /memo/list
    @GetMapping("/list")
    public void getList(Model model) {
        List<MemoDTO> list = memoService.getList();
        model.addAttribute("list", list);

    }

    // 특정 memo 조회 : /memo/read?mno=3
    @GetMapping({ "/read", "/update" })
    public void getRow(Long mno, Model model) {
        log.info("조회 요청 {} ", mno);
        MemoDTO dto = memoService.getRow(mno);
        model.addAttribute("dto", dto);
    }

    // memo 수정 : /memo/update?mno=3
    @PostMapping("/update")
    public String postMethodName(MemoDTO dto, RedirectAttributes rttr) {
        log.info("memo 수정 {} ", dto);
        Long mno = memoService.memoUpdate(dto);
        // 수정 완료 시 read화면 이동
        rttr.addAttribute("mno", mno);
        return "redirect:/memo/read";
    }

    // memo 추가 : /memo/new
    @GetMapping("/new")
    public void getNew() {
        log.info("새 메모 작성 폼 요청");
    }

    @PostMapping("/new")
    public String postNew(MemoDTO dto, RedirectAttributes rttr) {
        log.info("새 메모 작성 {} ", dto);
        Long mno = memoService.memoCreate(dto);
        rttr.addFlashAttribute("msg", mno);
        return "redirect:/memo/list";
    }

    // memo 삭제 : /memo/remove?mno=3
    @GetMapping("/remove")
    public String getRemove(Long mno) {
        log.info("Memo 삭제 요청 {}", mno);
        memoService.memoDelete(mno);
        return "redirect:/memo/list";
    }

}
