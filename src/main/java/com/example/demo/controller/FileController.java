package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
    private String uploadFolder;

    @PostMapping("/upload-email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        try {
            // 경로를 절대경로로 변환
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            // 업로드 폴더가 없으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 이메일을 파일 이름에 적합한 형태로 변환
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            // 업로드 폴더에 .txt 이름 설정
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt");
            System.out.println("File path: " + filePath); // 디버깅용 출력

            // 파일에 데이터 쓰기
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일제목: " + subject); // 제목 작성
                writer.newLine(); // 줄바꿈
                writer.write("요청메시지:"); // 요청 메시지 작성
                writer.newLine();
                writer.write(message); // 메시지 작성
            }

            // 성공적으로 업로드된 경우 플래시 속성 설정
            redirectAttributes.addFlashAttribute("message", "메일내용이 성공적으로 업로드되었습니다!");
        } catch (IOException e) {
            // 입출력 예외가 발생한 경우
            e.printStackTrace(); // 오류 스택 트레이스를 출력합니다.
            // 에러 메시지를 설정하고 에러 페이지로 이동합니다.
            redirectAttributes.addFlashAttribute("error", "업로드 중 오류가 발생했습니다: " + e.getMessage());
            return "/error_page/article_error";
        }
        // 업로드가 성공적으로 완료되면 "upload_end" 뷰를 반환합니다.
        return "upload_end";
    }
}

/* @Controller
public class FileController {

    @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
    private String uploadFolder;

    @PostMapping("/upload-email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        try {
            // 경로를 절대경로로 변환
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            // 업로드 폴더가 없으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 이메일을 파일 이름에 적합한 형태로 변환
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            // 업로드 폴더에 .txt 이름 설정
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt");
            System.out.println("File path: " + filePath); // 디버깅용 출력

            // 파일에 데이터 쓰기
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일제목: " + subject); // 제목 작성
                writer.newLine(); // 줄바꿈
                writer.write("요청메시지:"); // 요청 메시지 작성
                writer.newLine();
                writer.write(message); // 메시지 작성
            }

            // 성공적으로 업로드된 경우 플래시 속성 설정
            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
            return "upload_end"; // 업로드 완료 페이지로 리다이렉트

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
            return "/error_page/article_error"; // 오류 처리 페이지로 리다이렉트
        }
    }

    
} */



