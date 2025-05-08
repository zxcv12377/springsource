package com.example.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.entity.Book;
import com.example.rest.entity.QBook;
import com.example.rest.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public Long insert(BookDTO dto) {
        Book book = modelMapper.map(dto, Book.class);
        return book.getCode();
    }

    public BookDTO read(Long code) {
        Book book = bookRepository.findById(code).get();
        return modelMapper.map(book, BookDTO.class);
    }

    public PageResultDTO<BookDTO> readAll(PageRequestDTO pageRequestDTO) {

        Pageable pagealbe = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("code").descending());
        Page<Book> result = bookRepository
                .findAll(bookRepository.makepredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()), pagealbe);

        List<BookDTO> books = result.get().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResultDTO.<BookDTO>withAll()
                .dtoList(books)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO).build();
        // List<Book> list = bookRepository.findAll();
        // List<BookDTO> books = list.stream()
        // .map(book -> modelMapper.map(book, BookDTO.class))
        // .collect(Collectors.toList());
        // return books;

        // List<BookDTO> books = new ArrayList<>();
        // bookRepository.findAll().forEach(book -> {
        // books.add(modelMapper.map(book, BookDTO.class));
        // });
        // return books;
    }

    public void modify(BookDTO dto) {
        Book book = bookRepository.findById(dto.getCode()).get();
        book.setPrice(dto.getPrice());
        bookRepository.save(book);
    }

    public void remove(Long code) {
        bookRepository.deleteById(code);
    }

    public Long create(BookDTO dto) {
        // Book book = Book.builder()
        // .title(dto.getTitle())
        // .author(dto.getAuthor())
        // .price(dto.getPrice())
        // .build();
        Book book = modelMapper.map(dto, Book.class);
        return bookRepository.save(book).getCode();
    }

}
