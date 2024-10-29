package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity 
@Table(name = "testdb") 
@Data 

public class TestDB {
    @Id // 해당 변수가 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당
    private Long id;
    
    @Column(nullable = true) // 테이블의 컬럼 설정 값을 명시
    private String name;
}
