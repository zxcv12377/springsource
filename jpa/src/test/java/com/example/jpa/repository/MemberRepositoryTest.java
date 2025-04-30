package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.QMember;
import com.example.jpa.entity.Member.RoleType;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .name("홍길동" + i)
                    .roleType(RoleType.USER)
                    .age(i + 5)
                    .description("user" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void queryDslTest() {
        QMember member = QMember.member;
        System.out.println(memberRepository.findAll(member.name.eq("홍길동3")));
        System.out.println(memberRepository.findAll(member.age.gt(15)));
        System.out.println(memberRepository.findAll(member.roleType.eq(RoleType.USER)));
        System.out.println(memberRepository.findAll(member.name.contains("길동")));
        System.out.println(memberRepository.findAll(Sort.by("no").descending()));

    }
}
