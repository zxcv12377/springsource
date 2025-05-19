package com.example.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{mno}")
    public Long postReview(@RequestBody ReviewDTO dto) {
        log.info("리뷰 등록 요청 {}", dto);
        return reviewService.createReview(dto);
    }

    @GetMapping("/{mno}/all")
    public List<ReviewDTO> getList(@PathVariable Long mno) {
        log.info("review 요청 {}", mno);
        return reviewService.getList(mno);
    }

    @GetMapping("/{mno}/{rno}")
    public ReviewDTO getReview(@PathVariable Long rno, @PathVariable Long mno) {
        log.info("review 가져오기 {} {}", rno, mno);
        return reviewService.getReview(rno);
    }

    @PutMapping("/{mno}/{rno}")
    public ReviewDTO putReview(@PathVariable Long rno, @RequestBody ReviewDTO dto) {
        log.info("review 수정 {} {}", rno, dto);
        ReviewDTO update = reviewService.updateReview(dto);
        return update;
    }

    @DeleteMapping("/{mno}/{rno}")
    public Long remove(@PathVariable Long rno) {
        log.info("review 제거 {}", rno);
        reviewService.removeReply(rno);
        return rno;
    }
}
