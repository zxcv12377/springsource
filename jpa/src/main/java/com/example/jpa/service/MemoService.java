package com.example.jpa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemoService {
    // Repository 메소드 호출한 후 결과 받기

    private final MemoRepository memoRepository;

    public List<MemoDTO> getList() {
        // memoRepository.findAll().forEach(memo -> System.out.println(memo));
        List<Memo> list = memoRepository.findAll();
        // Memo -> MemoDTO 욺기기

        // List<MemoDTO> memos = new ArrayList<>();
        // for (Memo memo : list) {
        // MemoDTO dto = MemoDTO.builder()
        // .mno(memo.getMno())
        // .memoText(memo.getMemoText())
        // .build();
        // memos.add(dto);
        // }

        // list.stream().forEach(memo -> System.out.println(memo));
        List<MemoDTO> memos = list.stream()
                .map(memo -> entityToDto(memo))
                .collect(Collectors.toList());

        return memos;
    }

    private MemoDTO entityToDto(Memo memo) {
        MemoDTO dto = MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDate(memo.getCreatedDate())
                .updateDate(memo.getUpdateDate())
                .build();
        return dto;
    }

    private Memo dtotoEntity(MemoDTO dto) {
        Memo memo = Memo.builder()
                .mno(dto.getMno())
                .memoText(dto.getMemoText())
                .build();
        return memo;
    }

    public MemoDTO getRow(Long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
        MemoDTO dto = entityToDto(memo);
        return dto;
    }

    public Long memoUpdate(MemoDTO dto) {
        Memo memo = memoRepository.findById(dto.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(dto.getMemoText());
        // update 실행 => 수정된 Memo return
        memoRepository.save(memo);
        return memo.getMno();
    }

    public void memoDelete(Long mno) {
        memoRepository.deleteById(mno);
    }

    public Long memoCreate(MemoDTO dto) {
        // 새로 입력할 memo는 MemoDTO에 저장
        Memo memo = dtotoEntity(dto);
        memo = memoRepository.save(memo);
        return memo.getMno();
    }
}
