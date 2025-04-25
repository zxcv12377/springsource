package com.example.book.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Book book = Book.builder()
                    .title("삭제해라 애송이" + i)
                    .author("심현진")
                    .price(15000 + (i * 500))
                    .build();

            bookRepository.save(book);

        });
    }

    @Test
    public void testRead() {
        Book book = bookRepository.findById(1L).get();
        System.out.println(book);
    }

    @Test
    public void testReadAll() {
        bookRepository.findAll().forEach(book -> {
            System.out.println(book);
        });
    }

    @Test
    public void testUpdate() {
        Book book = bookRepository.findById(1L).get();
        book.setTitle("날 미치게하는군");
        bookRepository.save(book);
    }

    @Test
    public void testRemove() {
        bookRepository.deleteById(1L);
    }
}
