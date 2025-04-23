package com.example.relation.entity.team;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 하나의 팀에는 여러 회원이 소속된다

@Getter
@Setter
@Builder
@ToString(exclude = "members")
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String teamName;
    // 양방향 연관관계
    // @OneToMany : One 테이블만 select실행
    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<TeamMember> members = new ArrayList<>();
}
