package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.ToDo;
import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    // select문을 대신할 메소드 생성
    // 완료/미완료 목록 추출
    List<ToDo> findByCompleted(Boolean completed);

    // 중요/안중요 목록 추출
    List<ToDo> findByImportanted(Boolean importanted);
}
