package com.example.demo.model.service;

import lombok.*;
import com.example.demo.model.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "password") // 민감 정보 제외
public class AddMemberRequest {
    private String name;
    private String email;
    private String password;
    private String age; // 숫자 타입으로 변경
    private String mobile;
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



