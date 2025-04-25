package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("writer" + i)
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    public void updateTest() {
        Board board = boardRepository.findById(1L).get();
        board.setContent("content update");
        boardRepository.save(board);
    }

    @Test
    public void readTest() {
        Board board = boardRepository.findById(1L).get();
        System.out.println(board);
    }

    @Test
    public void listTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(5L);
    }

    @Test
    public void queryMethodTest() {
        // System.out.println(boardRepository.findByWriter("writer2"));
        // System.out.println(boardRepository.findByTitle("title7"));
        // System.out.println(boardRepository.findByWriterContaining("10"));
        // System.out.println(boardRepository.findByWriterEndingWith("writer"));
        // System.out.println(boardRepository.findByWriterStartingWith("writer"));
        // System.out.println(boardRepository.findByWriterContainingOrContentContaining("6",
        // "update"));
        // System.out.println(boardRepository.findByWriterContainingAndContentContaining("6",
        // "update"));
        System.out.println(boardRepository.findByBnoGreaterThan(5L));
        // System.out.println(boardRepository.findByBnoGreaterThanOrderByBnoDesc(5L));
        // System.out.println(boardRepository.findByBnoBetween(2L, 5L));
        // System.out.println(boardRepository.findByBnoGreaterThanAndLessThan(2L, 5L));

    }

}
