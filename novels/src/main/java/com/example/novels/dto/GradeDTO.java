package com.example.novels.dto;

import com.example.novels.entity.Member;

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
public class GradeDTO {
    private Long id;

    private int rating;

    private NovelDTO novel;

    private Member member;
}
