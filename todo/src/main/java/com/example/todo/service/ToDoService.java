package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.ToDoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    private final ModelMapper modelMapper;

    public List<ToDoDTO> readToDo(boolean completed) {
        List<ToDo> list = toDoRepository.findByCompleted(completed);
        // List<ToDoDTO> dtoList = new ArrayList<>();
        // list.forEach(todo -> {
        // ToDoDTO dto = modelMapper.map(dtoList, ToDoDTO.class);
        // dtoList.add(dto);
        // });
        List<ToDoDTO> dtoList = list.stream()
                .map(todo -> modelMapper.map(todo, ToDoDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }
}
