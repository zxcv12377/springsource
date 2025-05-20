package com.example.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.total.MovieImageReviewRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long>, MovieImageReviewRepository {
    // movie번호를 기준으로 이미지 제거

    @Modifying // delete, update 시 반드시 작성
    @Query("DELETE FROM MovieImage mi WHERE mi.movie = :movie")
    void deleteByMovie(Movie movie);

    @Query(value = "SELECT * FROM MOVIE_IMAGE mi WHERE mi.path = to_char(sysdate-1, 'yyyy\\mm\\dd')", nativeQuery = true)
    List<MovieImage> getOldImages();
}
