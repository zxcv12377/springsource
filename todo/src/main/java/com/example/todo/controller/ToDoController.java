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
    }

}
