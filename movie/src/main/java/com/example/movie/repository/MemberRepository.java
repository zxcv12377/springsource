package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    @Modifying
    @Query("UPDATE Member m SET m.nickname = :nickname WHERE m.email= :email")
    void updateNickname(String nickname, String email);
}
