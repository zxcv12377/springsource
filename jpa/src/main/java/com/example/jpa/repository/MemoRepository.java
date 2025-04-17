package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

}
