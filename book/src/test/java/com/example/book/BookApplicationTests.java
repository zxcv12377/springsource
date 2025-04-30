package com.example.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.example.book.repository.BookRepository;

@SpringBootTest
class BookApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Test
	void contextLoads() {
		// QBook book = QBook.book;
		Pageable pagealbe = PageRequest.of(0, 10, Sort.by("code").descending());
		Page<Book> result = bookRepository.findAll(pagealbe);
		result.getContent().forEach(book -> System.out.println(book));
		System.out.println("전체 행 개수 : " + result.getTotalElements());
		System.out.println("전체 페이지 수 : " + result.getTotalPages());
	}

}
