package com.example.relation.entity.sports;

import com.example.relation.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "sportsMember")
@Builder

@Entity
public class Locker extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker", fetch = FetchType.LAZY)
    private SportsMember sportsMember;
}
