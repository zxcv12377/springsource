package com.example.movie.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.movie.dto.MovieImageDTO;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;

import lombok.extern.log4j.Log4j2;

// cron : 유닉스 계열 운영체제에서 사용하는 작업 스케쥴러

@Component
@Log4j2
public class FileCheckTask {
    @Autowired
    private MovieImageRepository movieImageRepository;

    @Value("${com.example.movie.upload.path}")
    private String uploadPath;

    // 전일자 폴더의 리스트 추출
    private String getFolderYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String str = yesterday.format((DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return str.replace("-", File.separator);
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkFile() {
        log.info("File Check Task..........");

        List<MovieImage> oldImages = movieImageRepository.getOldImages();
        List<MovieImageDTO> movieImageDTOs = oldImages.stream().map(oldImage -> {
            return MovieImageDTO.builder()
                    .inum(oldImage.getInum())
                    .uuid(oldImage.getUuid())
                    .imgName(oldImage.getImgName())
                    .path(oldImage.getPath())
                    .build();
        }).collect(Collectors.toList());
        // java.nio.Path
        List<Path> fileListPaths = movieImageDTOs.stream()
                .map(dto -> Paths.get(uploadPath, dto.getImageURL(), dto.getUuid() + "_" + dto.getImgName()))
                .collect(Collectors.toList());

        movieImageDTOs.stream()
                .map(dto -> Paths.get(uploadPath, dto.getImageURL(), "s_" + dto.getUuid() + "_" + dto.getImgName()))
                .forEach(p -> {
                    fileListPaths.add(p);
                    log.warn(uploadPath, p);
                });

        File targetDir = Paths.get(uploadPath, getFolderYesterday()).toFile();
        File[] removeFiles = targetDir.listFiles(f -> fileListPaths.contains(f.toPath()) == false);

        if (removeFiles != null) {
            for (File file : removeFiles) {
                log.warn(file.getAbsolutePath());
                file.delete();
            }
        }
    }
}
