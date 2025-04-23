package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.ToDo;

@SpringBootTest
public class ToDoRepositoryTest {
    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ToDo toDo = ToDo.builder()
                    .content("Anything" + i)
                    .completed(false)
                    .importanted(false)
                    .build();

            toDoRepository.save(toDo);
        });
    }

    @Test
    public void allReadTest() {
        toDoRepository.findAll().forEach(toDo -> {
            System.out.println(toDo);
        });
    }

    @Test
    public void readTest() {
        ToDo toDo = toDoRepository.findById(2L).get();
        System.out.println(toDo);
    }

    @Test
    public void readTest2() {
        toDoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void readTest3() {
        toDoRepository.findByImportanted(false).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void updateTest() {
        ToDo toDo = toDoRepository.findById(2L).get();
        toDo.setCompleted(true);
        toDoRepository.save(toDo);
    }

    @Test
    public void deleteTest() {
        toDoRepository.deleteById(1L);
    }
}
