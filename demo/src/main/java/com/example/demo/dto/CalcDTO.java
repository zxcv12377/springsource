package com.example.demo.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CalcDTO {
    private int num1;
    private int num2;
    private String op;
    private int result;
}
