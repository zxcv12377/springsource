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
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Book book = Book.builder()
                    .title("삭제해라 애송이" + i)
                    .author("심현진")
                    .price(15000 + (i * 1000))
                    .build();

            bookRepository.save(book);

        });
    }
}
