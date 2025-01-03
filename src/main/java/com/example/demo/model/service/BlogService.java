/*package com.example.demo.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Article;
import com.example.demo.model.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
public class BlogService {
    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    private final BlogRepository blogRepository; // 리포지토리 선언

    public List<Article> findAll() { // 게시판 전체 목록 조회
        return blogRepository.findAll();
    }
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }
} */

/*package com.example.demo.model.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Article;
import com.example.demo.model.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
public class BlogService {
    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    private final BlogRepository blogRepository; // 리포지토리 선언

    public List<Article> findAll() { // 게시판 전체 목록 조회
        return blogRepository.findAll();
    }
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }
    
    public Optional<Article> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository.findById(id);
    }
    public void update(Long id, AddArticleRequest request) {
        Optional<Article> optionalArticle = blogRepository.findById(id); // 단일 글 조회
        optionalArticle.ifPresent(article -> { // 값이 있으면
            article.update(request.getTitle(), request.getContent()); // 값을 수정
            blogRepository.save(article); // Article 객체에 저장
        });
    }
}*/

/* 
package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
//import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
//import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 자동 생성
public class BlogService {

    private final BoardRepository blogRepository; // 리포지토리 선언

    //public List<Article> findAll() { // 게시판 전체 목록 조회
    //    return blogRepository.findAll();
    //}

    //public Article save(AddArticleRequest request) { // 게시글 저장
    //    return blogRepository.save(request.toEntity());
    //}

    //public Optional<Article> findById(Long id) { // 게시판 특정 글 조회
    //   return blogRepository.findById(id);
    //}

    //public void update(Long id, AddArticleRequest request) { // 게시글 수정
    //   Optional<Article> optionalArticle = blogRepository.findById(id); // 단일 글 조회
    //    optionalArticle.ifPresent(article -> { // 값이 있으면
    //        article.update(request.getTitle(), request.getContent()); // 값을 수정
    //        blogRepository.save(article); // 수정된 객체를 저장
    //    });
    //}

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
    public List<Board> findAll() {
        return blogRepository.findAll();
    }
    
    public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository.findById(id);
    }   
}
*/

package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BoardRepository;
import com.example.demo.model.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor // 생성자 자동 생성
public class BlogService {
@Autowired


    private final BoardRepository blogRepository; // 리포지토리 선언

    public void delete(Long id) { // 게시글 삭제
        blogRepository.deleteById(id);
    }

    public List<Board> findAll() { // 게시판 전체 목록 조회
        return blogRepository.findAll();
    }

    public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository.findById(id);
    }

    public Board save(AddArticleRequest request){
        // DTO가 없는 경우 이곳에 직접 구현 가능
        //return boardRepository.save(request.toEntity());
        return blogRepository.save(request.toEntity());
    }

    public Page<Board> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
        }

    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return blogRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        } // LIKE 검색 제공(대소문자 무시)
            
        public void update(Long id, AddArticleRequest request) {
            // 1. 게시글을 id로 찾기
            Board board = blogRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    
            // 2. 제목과 내용 수정
            board.setTitle(request.getTitle());    // 제목 수정
            board.setContent(request.getContent()); // 내용 수정
    
            // 3. 추가된 필드 수정 (null 체크 후 수정)
            // `request.getUser()`가 null이 아니면 수정
            if (request.getUser() != null) {
                board.setUser(request.getUser());
            }
    
            // `request.getNewdate()`가 null이 아니면 수정
            if (request.getNewdate() != null) {
                board.setNewdate(request.getNewdate());
            }
    
            // `request.getCount()`가 null이 아니면 수정
            if (request.getCount() != null) { // Count는 숫자이므로, 0을 기본값으로 체크
                board.setCount(request.getCount());
            }
    
            // `request.getLikec()`가 null이 아니면 수정
            if (request.getLikec() != null) { // Likec도 숫자이므로, 0을 기본값으로 체크
                board.setLikec(request.getLikec());
            }
    
            // 4. 수정된 게시글을 저장
            blogRepository.save(board);
        }

} 

