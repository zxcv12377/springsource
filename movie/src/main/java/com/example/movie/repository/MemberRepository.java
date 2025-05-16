package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
