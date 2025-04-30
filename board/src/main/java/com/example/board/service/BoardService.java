package com.example.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    public PageResultDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.list(pageable);
        // List<Object[]> result = boardRepository.list();
        Function<Object[], BoardDTO> fn = (entity -> entityToDTO((Board) entity[0], (Member) entity[1],
                (Long) entity[2]));

        List<BoardDTO> dtoList = result.stream().map(fn).collect(Collectors.toList());
        Long totalCount = result.getTotalElements();
        PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResultDTO;
    }

    // 번호, 제목, 내용, 작성자, 댓글개수
    public BoardDTO readBoard(Long bno) {
        Object[] entity = boardRepository.getBoardByBno(bno);

        return entityToDTO((Board) entity[0], (Member) entity[1], (Long) entity[2]);
    }

    public void createBoard() {

    }

    private BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .email(member.getEmail())
                .name(member.getName())
                .replyCount(replyCount)
                .build();
        return dto;
    }
}
