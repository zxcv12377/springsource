package com.example.novels.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.novels.dto.NovelDTO;
import com.example.novels.dto.PageRequestDTO;
import com.example.novels.dto.PageResultDTO;
import com.example.novels.entity.Genre;
import com.example.novels.entity.Novel;
import com.example.novels.repository.GradeRepository;
import com.example.novels.repository.NovelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class NovelService {

    private final NovelRepository novelRepository;

    private final GradeRepository gradeRepository;

    public Long avaUpdate(NovelDTO dto) {
        Novel novel = novelRepository.findById(dto.getId()).get();
        novel.changeAvailable(dto.isAvailable());
        return novelRepository.save(novel).getId();

    }

    public void novelRemove(Long id) {
        Novel novel = novelRepository.findById(id).get();
        gradeRepository.deleteByNovel(novel);
        novelRepository.deleteById(id);
    }

    public Long novelInsert(NovelDTO novelDTO) {
        Novel novel = Novel.builder()
                .title(novelDTO.getTitle())
                .author(novelDTO.getAuthor())
                .publishedDate(novelDTO.getPublishedDate())
                .available(novelDTO.isAvailable())
                .genre(Genre.builder().id(novelDTO.getGid()).build())
                .build();
        // Novel novel = dtoToEntity(novelDTO);
        return novelRepository.save(novel).getId();
    }

    public PageResultDTO<NovelDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(),
                Sort.by("id").descending());
        Page<Object[]> list = novelRepository.list(pageable);
        List<NovelDTO> dtoList = list.get().map(arr -> {
            Novel novel = (Novel) arr[0];
            Genre genre = (Genre) arr[1];
            Double ratingAvg = (Double) arr[2];

            NovelDTO dto = NovelDTO.builder()
                    .id(novel.getId())
                    .title(novel.getTitle())
                    .author(novel.getAuthor())
                    .publishedDate(novel.getPublishedDate())
                    .available(novel.isAvailable())
                    .gid(genre.getId())
                    .genreName(genre.getName())
                    .rating(ratingAvg != null ? ratingAvg.intValue() : 0)
                    .build();
            return dto;
        }).collect(Collectors.toList());

        long totalCount = list.getTotalElements();
        return PageResultDTO.<NovelDTO>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDTO(pageRequestDTO)
                .build();
    }

    public NovelDTO getRow(Long id) {
        Object[] result = novelRepository.getNovelById(id);
        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        Double ratingAvg = (Double) result[2];

        return entityToDto(novel, genre, ratingAvg);
    }

    private NovelDTO entityToDto(Novel novel, Genre genre, Double ratingAvg) {
        NovelDTO dto = NovelDTO.builder()
                .id(novel.getId())
                .title(novel.getTitle())
                .author(novel.getAuthor())
                .publishedDate(novel.getPublishedDate())
                .available(novel.isAvailable())
                .gid(novel.getGenre().getId())
                .genreName(novel.getGenre().getName())
                .rating(ratingAvg != null ? ratingAvg.intValue() : 0)
                .build();
        return dto;
    }

    // private Novel dtoToEntity(NovelDTO dto) {
    // Novel novel = Novel.builder()
    // .id(dto.getId())
    // .title(dto.getTitle())
    // .author(dto.getAuthor())
    // .publishedDate(dto.getPublishedDate())
    // .available(dto.isAvailable())
    // .genre(Genre.builder().id(dto.getGid()).build())
    // .build();
    // return novel;
    // }
}
