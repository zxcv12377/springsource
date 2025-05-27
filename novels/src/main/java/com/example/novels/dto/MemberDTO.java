package com.example.novels.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String email;

    private String pw;
    private String nickname;
    private boolean social;
}
