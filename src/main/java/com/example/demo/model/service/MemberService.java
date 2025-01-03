package com.example.demo.model.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.model.service.AddMemberRequest;

import jakarta.validation.Valid;

@Service
@Validated
@Transactional // 트랜잭션 처리
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 중복 회원 검증
    private void validateDuplicateMember(AddMemberRequest request) {
            Member findMember = memberRepository.findByEmail(request.getEmail());
        if (findMember != null) {
        throw new IllegalStateException("이미 가입된 회원입니다."); // 예외 처리
    }
}

public Member saveMember(@Valid AddMemberRequest request){
    validateDuplicateMember(request); // 이메일 체크

    String encodedPassword = passwordEncoder.encode(request.getPassword());
    request.setPassword(encodedPassword); // 암호화된 비밀번호 설정
    return memberRepository.save(request.toEntity());
}

public Member loginCheck(String email, String rawPassword) {
    Member member = memberRepository.findByEmail(email); // 이메일 조회
    if (member == null) {
        throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
    }
    if (!passwordEncoder.matches(rawPassword, member.getPassword())) { // 비밀번호 확인
        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
    return member; // 인증 성공 시 회원 객체 반환
}
}

