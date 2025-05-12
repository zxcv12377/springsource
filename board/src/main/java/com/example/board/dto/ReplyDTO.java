package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;
    private String text;
    private String replyerEmail;
    private String replyerName;
    private Long bno;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
