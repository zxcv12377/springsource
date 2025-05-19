package com.example.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public Long createReview(ReviewDTO dto) {
        Review review = dtoToEntity(dto);

        return reviewRepository.save(review).getRno();
    }

    public ReviewDTO getReview(Long rno) {
        Review review = reviewRepository.findById(rno).get();
        return entityToDto(review);
    }

    public ReviewDTO updateReview(ReviewDTO dto) {
        Review review = reviewRepository.findById(dto.getRno()).orElseThrow();
        review.changeGrade(dto.getGrade());
        review.changeText(dto.getText());
        review = reviewRepository.save(review);
        return entityToDto(review);

    }

    public void removeReply(Long rno) {
        reviewRepository.deleteById(rno);
    }

    public List<ReviewDTO> getList(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        List<ReviewDTO> list = result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
        return list;
        // List<Review> result = reviewRepository.findByMovieMno(mno);
        // return result.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    private Review dtoToEntity(ReviewDTO dto) {
        Review review = Review.builder()
                .rno(dto.getRno())
                .grade(dto.getGrade())
                .text(dto.getText())
                .member(Member.builder().mid(dto.getMid()).build())
                .movie(Movie.builder().mno(dto.getMno()).build())
                .build();

        return review;
    }

    private ReviewDTO entityToDto(Review review) {
        ReviewDTO dto = ReviewDTO.builder()
                .rno(review.getRno())
                .grade(review.getGrade())
                .text(review.getText())
                .createdDate(review.getCreatedDate())
                .updatedDate(review.getUpdatedDate())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .build();

        return dto;
    }

}
