package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Board;
import com.example.jpa.entity.QBoard;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void queryDslTest() {
        // Q 파일 사용
        QBoard board = QBoard.board;
        // where b.title = 'title1'
        // System.out.println(boardRepository.findAll(board.title.eq("title1")));

        // where b.title like 'title%'
        // System.out.println(boardRepository.findAll(board.title.startsWith("title")));

        // where b.title like '%title1'
        // System.out.println(boardRepository.findAll(board.title.endsWith("title1")));

        // where b.title like '%title%'
        // System.out.println(boardRepository.findAll(board.title.contains("title")));

        // where b.title like '%title% and b.bno > 0 order by desc'
        // Iterable<Board> boards =
        // boardRepository.findAll(board.title.contains("title")
        // .and(board.bno.gt(0L)), Sort.by("bno").descending());
        // System.out.println(boards);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(board.bno.gt(0L), pageable);
        System.out.println("page size " + result.getSize());
        System.out.println("page TotalPage " + result.getTotalPages());
        System.out.println("page Elements " + result.getTotalElements());
        System.out.println("page Content " + result.getContent());
    }

    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
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
        // boardRepository.findAll().forEach(board -> System.out.println(board));

        Pageable pageable = PageRequest.of(3, 10, Sort.by("bno").descending());
        boardRepository.findAll(pageable).forEach(board -> System.out.println(board));
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
        // System.out.println(boardRepository.findByBnoGreaterThan(5L));
        // System.out.println(boardRepository.findByBnoGreaterThanOrderByBnoDesc(5L));
        // System.out.println(boardRepository.findByBnoBetween(2L, 5L));
        // System.out.println(boardRepository.findByBnoGreaterThanAndLessThan(2L, 5L));
        List<Object[]> result = boardRepository.findByTitle2("title");
        for (Object[] objects : result) {
            String title = (String) objects[0];
            String writer = (String) objects[1];
            System.out.println(Arrays.toString(objects));
            System.out.println(title + " " + writer);
            System.out.println("=================");

        }
    }

}
