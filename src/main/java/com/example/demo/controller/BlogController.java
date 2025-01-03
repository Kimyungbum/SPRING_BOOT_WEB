package com.example.demo.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
//import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller // 컨트롤러 어노테이션 명시

public class BlogController {

    @Autowired
    BlogService blogService;

     @Autowired
    private HttpSession session;

    /*@GetMapping("/article_list") // 게시판 링크 지정
    public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }*/

    /*@GetMapping("/article_edit/{id}") // 게시판 링크 지정
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
            
        if (list.isPresent()) {
            model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "redirect:/error_page/article_error"; // 오류 처리 페이지로 연결(이름 수정됨)
        }
        return "article_edit"; // .HTML 연결
    }*/

    
    /*@PutMapping("/api/board_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/board_list"; // 글 수정 이후 .html 연결
    }*/

    /*@DeleteMapping("/api/board_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }*/
       
    /*@GetMapping("/board_list") // 게시판 리스트 조회
    public String boardList(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String keyword) {
            String userId = (String) session.getAttribute("userId");
            String email = (String) session.getAttribute("email"); 
            if (userId == null) {
                return "redirect:/member_login"; // 로그인 페이지로 리다이렉션
                }
                System.out.println("세션 userId: " + userId);
    
    PageRequest pageable = PageRequest.of(page, 3); // 한 페이지에 게시글 3개 표시
    Page<Board> list;

    if (keyword.isEmpty()) {
        list = blogService.findAll(pageable); // 키워드 없는 경우 전체 출력
    } else {
        list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
    }

    model.addAttribute("boards", list);                // 게시글 리스트
    model.addAttribute("totalPages", list.getTotalPages()); // 전체 페이지 수
    model.addAttribute("currentPage", page);           // 현재 페이지 번호
    model.addAttribute("keyword", keyword);            // 검색 키워드
    model.addAttribute("email", email); 

    return "board_list"; // 연결할 HTML 파일
}*/

@GetMapping("/board_list")
    public String boardList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Board> boardPage = (keyword == null || keyword.isEmpty()) ? 
                blogService.findAll(pageable) : 
                blogService.searchByKeyword(keyword, pageable);

        int startNum = (page * pageSize) + 1;

        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());
        model.addAttribute("startNum", startNum);
        model.addAttribute("keyword", keyword);

        return "board_list"; // 게시글 목록 페이지
    }

    // 게시글 삭제 처리
    @PostMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id); // 서비스 클래스에서 삭제 처리
        return "redirect:/board_list"; // 삭제 후 게시글 목록 페이지로 리다이렉트
    }




    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
    Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
    if (list.isPresent()) {
        model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
    } else {
    // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/article_error"; // 오류 처리 페이지로 연결
                }
        return "board_view"; // .HTML 연결
        }

    @GetMapping("/board_write")
        public String board_write() {
        return "board_write";
        }

    @PostMapping("/api/boards") // 글쓰기 게시판 저장
        public String addboards(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/board_list"; // .HTML 연결
}

/*@GetMapping("/board_edit/{id}")
public String boardEdit(@PathVariable Long id, Model model) {
    Optional<Board> board = blogService.findById(id);
    if (board.isPresent()) {
        model.addAttribute("board", board.get());
        return "board_edit"; // 수정 화면으로 이동
    } else {
        return "redirect:/error_page/article_error"; // 오류 처리
    }
} */

// 글 수정 요청 처리
@PutMapping("/api/board_edit/{id}")
public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    blogService.update(id, request); // 수정 서비스 호출
    return "redirect:/board_list"; // 수정 후 리스트로 리다이렉션
} 


@GetMapping("/board_edit/{id}")
public String getEditBoard(@PathVariable Long id, Model model) {
    Board board = blogService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    model.addAttribute("board", board);
    return "board_edit";
}

// 게시글 수정 요청 처리
/*@PutMapping("/board_edit/{id}")
public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    blogService.update(id, request); // 수정 서비스 호출
    return "redirect:/board_list"; // 수정 후 리스트로 리다이렉션
}*/

/* @GetMapping("/board_list") // 게시판 링크 지정
    public String board_list(Model model) {
        List<Board> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("boards", list); // 모델에 추가
        return "board_list"; // .HTML 연결
    }

    @GetMapping("/board_edit/{id}") // 게시판 링크 지정
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
            
        if (list.isPresent()) {
            model.addAttribute("board", list.get()); // 존재하면 Article 객체를 모델에 추가
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "redirect:/error_page/article_error"; // 오류 처리 페이지로 연결(이름 수정됨)
        }
        return "board_edit"; // .HTML 연결
    } */

    
}


   
    



    
