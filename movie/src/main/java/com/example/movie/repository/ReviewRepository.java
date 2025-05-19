package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying // delete, update 시 반드시 작성
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie 아이디를 이용해 리뷰 가져오기
    @EntityGraph(attributePaths = "member", type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @EntityGraph(attributePaths = "member", type = EntityGraphType.FETCH)
    @Query("SELECT r FROM Review r WHERE r.movie.mno = :mno")
    List<Review> findByMovieMno(Long mno);

}
