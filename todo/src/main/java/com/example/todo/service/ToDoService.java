package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
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

    public Long changeCompleted(ToDoDTO dto) {
        ToDo todo = toDoRepository.findById(dto.getId()).get();
        todo.setCompleted(dto.isCompleted());
        return toDoRepository.save(todo).getId();
    }

    public ToDoDTO read(Long id) {
        ToDo todo = toDoRepository.findById(id).get();
        return modelMapper.map(todo, ToDoDTO.class);
    }

    public void delete(Long id) {
        toDoRepository.deleteById(id);
    }

    public Long create(ToDoDTO dto) {
        ToDo todo = modelMapper.map(dto, ToDo.class);
        return toDoRepository.save(todo).getId();
    }

    // for react display
    public List<ToDoDTO> readToDo2() {
        List<ToDo> list = toDoRepository.findAll(Sort.by("id").descending());

        List<ToDoDTO> dtoList = list.stream()
                .map(todo -> modelMapper.map(todo, ToDoDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }

    public ToDoDTO create2(ToDoDTO dto) {
        ToDo todo = modelMapper.map(dto, ToDo.class);
        ToDo newTodo = toDoRepository.save(todo);
        return modelMapper.map(newTodo, ToDoDTO.class);
    }
}
