package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void listAllTest() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10, "t", "애송이");
        PageResultDTO<BookDTO> result = bookService.readAll(pageRequestDTO);

        System.out.println(result.getDtoList());
        System.out.println("Total Page : " + result.getTotalPage());
        System.out.println("PageNumList : " + result.getPageNumList());
        System.out.println("isNext : " + result.isNext());
        System.out.println("isPrev : " + result.isPrev());

    }
}
