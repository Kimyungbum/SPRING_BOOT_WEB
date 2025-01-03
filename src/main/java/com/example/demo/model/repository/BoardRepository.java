package com.example.demo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
