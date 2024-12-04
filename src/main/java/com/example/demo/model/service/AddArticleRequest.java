package com.example.demo.model.service;

import lombok.*;
import com.example.demo.model.domain.Board;
//import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddArticleRequest {
    private String title;
    private String content;
    private String user;
    private String newdate; // 날짜 타입 변경
    private String count; // 숫자 타입으로 변경
    private String likec; // 숫자 타입으로 변경

    public Board toEntity() { // Board 객체 생성
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .newdate(newdate.toString()) // Board의 필드에 맞게 변환
                .count(count)
                .likec(likec)
                .build();
    }
}
