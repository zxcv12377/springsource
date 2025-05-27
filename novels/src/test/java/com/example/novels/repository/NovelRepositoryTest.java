package com.example.novels.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.novels.entity.Genre;
import com.example.novels.entity.Grade;
import com.example.novels.entity.Member;
import com.example.novels.entity.Novel;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class NovelRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SearchNovelRepositoryImpl searchNovelRepositoryImpl;

    @Test
    public void memberInsertTest() {
        LongStream.rangeClosed(1, 50).forEach((i) -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .pw("1111")
                    .nickname("nick" + i)
                    .social(false)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void gradeInsertTest() {

        IntStream.rangeClosed(1, 200).forEach(i -> {
            int rating = (int) (Math.random() * 5) + 1;
            Long novel_id = (long) (Math.random() * 320) + 1;
            int member_id = (int) (Math.random() * 50) + 1;
            Grade grade = Grade.builder()
                    .rating(rating)
                    // .novel(novelRepository.findById(novel_id).get())
                    .novel(Novel.builder().id(novel_id).build())
                    // .member(memberRepository.findById("user" + member_id + "@gmail.com").get())
                    .member(Member.builder().email("user" + member_id + "@gmail.com").build())
                    .build();

            gradeRepository.save(grade);
        });
    }

    @Test
    public void getNovelTest() {
        Object[] result = novelRepository.getNovelById(212L);
        // System.out.println(Arrays.toString(result));
        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        int avg = (int) result[2];
        System.out.println(novel);
        System.out.println(genre);
        System.out.println(avg);

    }

    @Test
    public void getNovelListTest() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id").descending());
        Page<Object[]> results = novelRepository.list(pageable);
        results.forEach(obj -> {
            System.out.println(Arrays.toString(obj));
        });
    }

}
