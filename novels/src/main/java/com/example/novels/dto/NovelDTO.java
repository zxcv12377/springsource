package com.example.novels.dto;

import java.time.LocalDate;

import com.example.novels.entity.Genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovelDTO {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private boolean available;

    // Genre
    private Long gid;
    private String genreName;

    // Grade
    private int rating;
}
