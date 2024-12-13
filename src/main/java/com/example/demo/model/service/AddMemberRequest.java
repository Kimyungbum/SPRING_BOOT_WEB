package com.example.demo.model.service;

import lombok.*;
import com.example.demo.model.domain.Member;

import jakarta.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "password") // 민감 정보 제외
public class AddMemberRequest {
    
    @NotBlank(message = "이름은 공백일 수 없습니다.") // 공백 불가
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "이름에는 특수문자나 숫자가 포함될 수 없습니다.") // 특수문자 및 숫자 불가
    private String name;

    @NotBlank(message = "이메일은 공백일 수 없습니다.") // 공백 불가
    @Email(message = "이메일 형식이 올바르지 않습니다.") // 이메일 형식 검사
    private String email;

    @NotBlank(message = "패스워드는 공백일 수 없습니다.") // 공백 불가
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "패스워드는 8자 이상, 대소문자 및 숫자를 포함해야 합니다.") // 패스워드 패턴
    private String password;

    @NotNull(message = "나이는 필수 항목입니다.") // 나이는 필수
    @Min(value = 19, message = "나이는 19세 이상이어야 합니다.")
    @Max(value = 90, message = "나이는 90세 이하이어야 합니다.")
    private String age;

    @Pattern(regexp = "^[0-9\\-\\s]*$", message = "모바일 번호는 숫자, 공백 및 '-'만 포함할 수 있습니다.") // 모바일 번호
    private String mobile;

    @NotBlank(message = "주소는 공백일 수 없습니다.") // 공백 가능
    private String address;

    public Member toEntity() { // Member 생성자를 통해 객체 생성
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(age)
                .mobile(mobile)
                .address(address)
                .build();
    }
}




