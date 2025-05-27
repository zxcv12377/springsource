package com.example.novels.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNovelRepository {
    // 하나 조회

    Object[] getNovelById(Long id);
    // 페이지 나누기 + 전체 조회 + 검색

    Page<Object[]> list(Pageable pageable);
}
