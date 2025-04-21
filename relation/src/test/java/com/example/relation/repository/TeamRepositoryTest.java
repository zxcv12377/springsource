package com.example.relation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import com.example.relation.repository.team.TeamMemberRepository;
import com.example.relation.repository.team.TeamRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {
        Team team = teamRepository.save(Team.builder()
                .teamName("team2").build());

        // teamMemberRepository.save(TeamMember.builder()
        // .userName("user1")
        // .team(team).build());
    }

    @Test
    public void insert2Test() {
        Team team = teamRepository.findById(3L).orElseThrow(EntityNotFoundException::new);

        teamMemberRepository.save(TeamMember.builder()
                .userName("user3")
                .team(team).build());
    }

    @Test
    public void readTest1() {
        // Team team
        Team team = teamRepository.findById(1L).get();
        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        System.out.println(team);
        System.out.println(teamMember);
    }

    @Test
    public void readTest2() {

        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        System.out.println(teamMember.getTeam());
    }

    @Test
    public void updateTest() {
        TeamMember teamMember = teamMemberRepository.findById(1L).get();
        Team team = teamRepository.findById(2L).get();
        teamMember.setTeam(team);
        teamMemberRepository.save(teamMember);
    }

    @Test
    public void deleteTest() {
        // 무결성 제약조건
        // teamRepository.deleteById(1L);

        // 해결
        // 1. 삭제하려고 하는 팀의 팀원들은 다른 팀으로 이동 혹은 null 지정
        // 2. 자식 삭제 후 부모 삭제

        TeamMember teamMember = teamMemberRepository.findById(2L).get();
        Team team = teamRepository.findById(2L).get();
        teamMember.setTeam(team);
        teamMemberRepository.save(teamMember);

        teamRepository.deleteById(1L);
    }

    // 양방향 연관관계 : @OneToMany
    // @Transactional
    @Test
    public void readBiTest1() {

        Team team = teamRepository.findById(2L).get();
        // LazyInitializationException
        // 해결법
        // 1. @Transactional 주인(N방향)이 누군지 정해주기
        // 2. fetchType 변경
        // - FetchType.EAGER (즉시) 관계 있는 테이블 정보를 즉시 가지고 나오기 => left join 처리
        // - FetchType.LAZY
        System.out.println(team);
        team.getMembers().forEach(member -> System.out.println(member));
    }
}
