package com.example.rest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemoDTO {
    private Long mno;
    private String memoText;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}
