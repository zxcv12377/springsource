package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.CalcDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller

public class AddController {

    @GetMapping("/calc")
    public void getCalc(CalcDTO calcDTO) {
        log.info("calc 페이지 요청");

    }

    @PostMapping("/calc")
    public String postCalc(CalcDTO calcDTO) {
        log.info("calc 연산 요청");
        log.info("{} {} {}", calcDTO.getNum1(), calcDTO.getOp(), calcDTO.getNum2());
        int result = 0;
        switch (calcDTO.getOp()) {
            case "+":
                result = calcDTO.getNum1() + calcDTO.getNum2();
                break;
            case "-":
                result = calcDTO.getNum1() - calcDTO.getNum2();
                break;
            case "*":
                result = calcDTO.getNum1() * calcDTO.getNum2();
                break;
            case "/":
                result = calcDTO.getNum1() / calcDTO.getNum2();
                break;
            default:
                break;
        }
        log.info("연산결과 {} {} {} = {}", calcDTO.getNum1(), calcDTO.getOp(), calcDTO.getNum2(), result);
        calcDTO.setResult(result);
        return "result";
    }

}
