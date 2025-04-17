package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.Student.Grade;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest // test용 클래스임을 알려주는 어노테이션
public class StudentRepositoryTest {

    @Autowired // = new studentRepository()
    private StudentRepository studentRepository;

    // CRUD test
    // Repository, Entity 확인
    // C(insert) : save(Entity)
    // U(update) : save(Entity)
    // 구별은 어떻게 하는가? 둘다 동일한 save() 호출
    // 원본과 변경된 부분이 있다면 update로 실행
    // R(read) : findById
    // D(delete) : delete(Entity)

    @Test // 테스트 메소드임 (테스트 메소드의 리턴 타입은 void 여야함)
    public void insertTest() {

        LongStream.range(1, 11).forEach(i -> {
            Student student = Student.builder()
                    .name("홍길동" + i)
                    .grade(Grade.JUNIOR)
                    .gender("M")
                    .build();

            // insert
            studentRepository.save(student);
        });

    }

    @Test
    public void updateTest() {
        // .findById() Select * from 테이블명 where id=1L
        Student student = studentRepository.findById(1L).get();
        student.setGrade(Grade.SENIOR);
        // update
        studentRepository.save(student);
    }

    @Test
    public void selectOneTest() {
        // Optional<Student> student = studentRepository.findById(1L);

        // if (student.isPresent()) {
        // System.out.println(student.get());
        // }
        // Student student = studentRepository.findById(1L).get();
        Student student = studentRepository.findById(3L).orElseThrow(EntityNotFoundException::new);
        System.out.println(student);

    }

    @Test
    public void selectAllTest() {
        // List<Student> list = studentRepository.findAll();
        // for (Student student : list) {
        // System.out.println(student);
        // }

        studentRepository.findAll().forEach(student -> System.out.println(student));

    }

    @Test
    public void deleteTest() {

        // Student student = studentRepository.findById(11L).get();
        // studentRepository.delete(student);
        studentRepository.deleteById(10L);
    }
}
