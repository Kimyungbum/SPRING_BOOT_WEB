package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.domain.Member;
import com.example.demo.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService; // 서비스 주입

    @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new"; // .HTML 연결
    }

    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(@Valid @ModelAttribute AddMemberRequest request, BindingResult bindingResult, Model model) {
        memberService.saveMember(request); // 멤버 저장 로직 호출
        return "join_end"; // .HTML 연결
    } 


    @GetMapping("/member_login") // 로그인 페이지 연결
    public String member_login() {
        return "login"; // .HTML 연결
    }

    @PostMapping("/api/login_check") // 아이디와 패스워드 로그인 체크
    public String checkMembers(
            @ModelAttribute AddMemberRequest request,
            Model model,
            HttpServletRequest httpRequest,
            HttpServletResponse response) {
        try {
            // 로그인 검증
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            if (member == null) {
                model.addAttribute("error", "Invalid email or password."); // 오류 메시지
                return "login"; // 로그인 실패 시 로그인 페이지로 이동
            }
    
            // 기존 세션 무효화
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                session.invalidate();
                Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID 초기화
                cookie.setPath("/");
                cookie.setMaxAge(0); // 쿠키 삭제
                response.addCookie(cookie);
            }
    
            // 새로운 세션 생성 및 설정
            session = httpRequest.getSession(true); // 새로운 세션 생성
            String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID 생성
            session.setAttribute("userId", sessionId); // 사용자 ID 설정
            session.setAttribute("email", request.getEmail()); // 이메일 설정
    
            model.addAttribute("member", member); // 회원 정보 전달
    
            return "redirect:/board_list"; // 로그인 성공 시 이동할 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 이동
        }
    }

    /*@GetMapping("/api/logout") // 로그아웃 버튼 동작
public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {
    try {
        // 기존 세션 가져오기(존재하지 않으면 null 반환)
        HttpSession session = request2.getSession(false);

        if (session != null) {
            // 기존 세션 무효화
            session.invalidate();

            // JSESSIONID 쿠키 초기화
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/"); // 쿠키 경로 설정
            cookie.setMaxAge(0); // 쿠키 삭제
            response.addCookie(cookie); // 응답으로 쿠키 전달

            // 새로운 세션 생성
            session = request2.getSession(true);
        }

        // 세션 초기화 후 확인 로그 출력
        System.out.println("세션 userId: " + session.getAttribute("userId"));

        return "login"; // 로그인 페이지로 리다이렉트
    } catch (IllegalArgumentException e) {
        // 에러 메시지 전달
        model.addAttribute("error", e.getMessage());
        return "login"; // 에러 발생 시 로그인 페이지로 리다이렉트
    }
}*/

@GetMapping("/api/logout")
public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {
    try {
        // 기존 세션 가져오기(존재하지 않으면 새 세션을 생성)
        HttpSession session = request2.getSession(false);

        // 세션이 존재하지 않으면 새로운 세션 생성
        if (session == null) {
            session = request2.getSession(true); // 새 세션 생성
        }

        // 기존 세션 무효화
        session.invalidate();

        // JSESSIONID 쿠키 초기화
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/"); // 쿠키 경로 설정
        cookie.setMaxAge(0); // 쿠키 삭제
        response.addCookie(cookie); // 응답으로 쿠키 전달

        // 새로운 세션 생성
        session = request2.getSession(true);

        // 세션 초기화 후 확인 로그 출력
        System.out.println("세션 userId: " + session.getAttribute("userId"));

        return "login"; // 로그인 페이지로 리다이렉트
    } catch (IllegalArgumentException e) {
        // 에러 메시지 전달
        model.addAttribute("error", e.getMessage());
        return "login"; // 에러 발생 시 로그인 페이지로 리다이렉트
    }
}


}
