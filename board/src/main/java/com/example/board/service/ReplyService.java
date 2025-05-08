package com.example.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public List<ReplyDTO> getList(Long bno) {
        Board board = Board.builder().bno(bno).build();
        List<Reply> result = replyRepository.findByBoardOrderByRno(board);
        return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());

    }

    // 댓글 가져오기

    public ReplyDTO getReply(Long rno) {
        Reply reply = replyRepository.findById(rno).get();
        return entityToDto(reply);
    }

    // 댓글 수정하기

    public Long update(ReplyDTO dto) {
        Reply reply = replyRepository.findById(dto.getRno()).get();
        reply.changeText(dto.getText());

        return replyRepository.save(reply).getRno();
    }

    // 댓글 삭제하기
    public void delete(Long rno) {
        replyRepository.deleteById(rno);
    }

    public Long create(ReplyDTO dto) {
        Reply reply = dtoToEntity(dto);

        return replyRepository.save(reply).getRno();
    }

    private ReplyDTO entityToDto(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .bno(reply.getBoard().getBno())
                .createdDate(reply.getCreatedDate())
                .build();
        return dto;
    }

    private Reply dtoToEntity(ReplyDTO dto) {
        Reply board = Reply.builder()
                .rno(dto.getBno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(Board.builder().bno(dto.getBno()).build())
                .build();
        return board;

    }
}
