package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Student;

// extends JpaRepository<Entity명, @Id타입>
public interface StudentRepository extends JpaRepository<Student, Long> {

}
