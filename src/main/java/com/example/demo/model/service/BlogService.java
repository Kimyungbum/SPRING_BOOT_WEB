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

package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Article;
import com.example.demo.model.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 자동 생성
public class BlogService {

    private final BlogRepository blogRepository; // 리포지토리 선언

    public List<Article> findAll() { // 게시판 전체 목록 조회
        return blogRepository.findAll();
    }

    public Article save(AddArticleRequest request) { // 게시글 저장
        return blogRepository.save(request.toEntity());
    }

    public Optional<Article> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository.findById(id);
    }

    public void update(Long id, AddArticleRequest request) { // 게시글 수정
        Optional<Article> optionalArticle = blogRepository.findById(id); // 단일 글 조회
        optionalArticle.ifPresent(article -> { // 값이 있으면
            article.update(request.getTitle(), request.getContent()); // 값을 수정
            blogRepository.save(article); // 수정된 객체를 저장
        });
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}

