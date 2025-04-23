package com.example.demo.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class MemberDTO {

    @Pattern(regexp = "(?=^[a-zA-Z])(?=.+\\d)[a-zA-Z.+\\d]{6,12}$", message = "아이디는 영대소문자, 특수문자, 숫자를 포함해서 6~12자리 입니다")
    @NotBlank(message = "아이디 확인")
    private String userid;
    @NotBlank(message = "패스워드 확인")
    private String password;

    @NotBlank(message = "이메일 확인")
    @Email(message = "이메일 형식 확인")
    private String email;
    // @Length(min = 2, max = 5, message = "이름은 2~5자리만 가능합니다")
    @Pattern(regexp = "^[가-힣]{2,5}", message = "이름은 2~5자리만 가능합니다")
    private String name;

    @NotNull(message = "나이를 입력해주세요")
    @Min(value = 0, message = "나이는 최소 0 이상이어야 합니다.")
    @Max(value = 140, message = "나이는 최대 140 이하여야 합니다.")
    private Integer age;

    private boolean check;
}
