package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.team.Team;
import com.example.jpa.entity.team.TeamMember;
import com.example.jpa.repository.team.TeamMemberRepository;
import com.example.jpa.repository.team.TeamRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {
        Team team = teamRepository.save(Team.builder()
                .teamName("team1").build());

        teamMemberRepository.save(TeamMember.builder()
                .userName("user1")
                .team(team).build());
    }

    @Test
    public void insert2Test() {
        Team team = teamRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        teamMemberRepository.save(TeamMember.builder()
                .userName("user2")
                .team(team).build());
    }
}
