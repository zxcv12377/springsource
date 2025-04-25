package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // List<Memo> findByMnoLessThan(Long mno);

    // List<Memo> findByMnoLessThanOrderByMnoDesc(Long mno);

    // List<Memo> findByMemoTextContaining(String memoText);

}
