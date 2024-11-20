package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter // setter는 없음(무분별한 변경 x)
@Entity // 아래 객체와 DB 테이블을 매핑. JPA가 관리
@Table(name = "board") // 테이블 이름을 지정. 없는 경우 클래스이름으로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부 생성자 접근 방지

public class Board {
    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 1씩 증가
    
    @Column(name = "id", updatable = false) // 수정 x
    private Long id;
    
    @Column(name = "content", nullable = false) // null x
    private String title = "";
    
    @Column(name = "title", nullable = false)
    private String content = "";
    
    @Column(name = "count", nullable = false)
    private String user = "";
    
    @Column(name = "user", nullable = false)
    private String newdate = "";
    
    @Column(name = "likec", nullable = false)
    private String count = "";
   
    @Column(name = "newdate", nullable = false)
    private String likec = "";


    @Builder // 생성자에 빌더 패턴 적용(불변성)
    public Board (String content, String title, String count, String user, String likec, String newdate){
    this.content = content;
    this.title = title;
    this.count = count;
    this.user = user;
    this.likec = likec;
    this.newdate = newdate;
    }
    public void update(String content, String title, String count, String user, String likec, String newdate) { // 현재 객체 상태 업데이트
        this.content = content;
        this.title = title;
        this.count = count;
        this.user = user;
        this.likec = likec;
        this.newdate = newdate;
        }
}
