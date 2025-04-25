package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Board;

import jakarta.persistence.NamedQuery;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // List<Board> findByWriter(String writer);

    // List<Board> findByTitle(String title);

    // @NamedQuery(query = "SELECT bno,content FROM BOARD WHERE WRITER='?'")
    // List<Board> jointest(String writer);

    // List<Board> findByWriterStartingWith(String writer);

    // List<Board> findByWriterEndingWith(String writer);

    // List<Board> findByWriterContaining(String writer);

    // List<Board> findByWriterContainingOrContentContaining(String writer, String
    // content);

    // List<Board> findByWriterContainingAndContentContaining(String writer, String
    // content);

    // List<Board> findByBnoGreaterThan(Long bno);

    // List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    // List<Board> findByBnoBetween(Long s, Long e);

    // List<Board> findByBnoGreaterThanAndLessThan(Long s, Long e);

    @Query("select b from Board b where b.writer = ?1")
    List<Board> findByWriter(String writer);

    @Query("select b from Board b where b.writer like ?1%")
    List<Board> findByWriterStartingWith(String writer);

    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining(String writer);

    // @Query("select b from Board b where b.bno > ?1")

    // sql 구문 형식 사용
    // @Query(value = "select * from board b where b.bno > ?1", nativeQuery = true)
    @NativeQuery(value = "select * from board b where b.bno > ?1")
    List<Board> findByBnoGreaterThan(Long bno);
}
