package com.nhaccuaquang.musique.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class SongDto {
    private Long id;
    private String name;
    private String link;
    private String thumbnail;
    private LocalDate releasedAt;
    private Integer status;
    private Long genre;

    public SongDto(Long id, String name, String link, String thumbnail, LocalDate releasedAt, Integer status, Long genre) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.thumbnail = thumbnail;
        this.releasedAt = releasedAt;
        this.status = status;
        this.genre = genre;
    }
}
