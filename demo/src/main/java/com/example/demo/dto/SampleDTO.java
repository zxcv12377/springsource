package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder

public class SampleDTO {
    private Long id;
    private String first;
    private String last;
    private LocalDateTime regDateTime;
}
