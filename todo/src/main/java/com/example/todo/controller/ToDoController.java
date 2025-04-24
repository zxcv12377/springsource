package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.service.ToDoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/todo")
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("/list")
    public void getList(@RequestParam(defaultValue = "0") boolean completed, Model model) {
        log.info("미완료 todo 가져오기 {} ", completed);
        List<ToDoDTO> todos = toDoService.readToDo(completed);
        model.addAttribute("todos", todos);
        // 어떤(완료, 미완료) 목록을 보여줄 것인지
        model.addAttribute("completed", completed);
    }

    @GetMapping("/read")
    public void getRead(Long id, Model model) {
        log.info("조회 {}", id);
        ToDoDTO dto = toDoService.read(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postCompleted(ToDoDTO dto, RedirectAttributes rttr) {
        log.info("수정 {}", dto);
        Long id = toDoService.changeCompleted(dto);
        rttr.addAttribute("id", id);
        return "redirect:/todo/read";
    }

    @GetMapping("/remove")
    public String getDelete(Long id) {
        log.info("삭제 {}", id);
        toDoService.delete(id);
        return "redirect:/todo/list";
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("todo 작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(ToDoDTO dto, RedirectAttributes rtts) {
        log.info("todo 입력 {}", dto);
        Long id = toDoService.create(dto);

        rtts.addAttribute("id", id);
        return "redirect:/todo/read";
    }

}
