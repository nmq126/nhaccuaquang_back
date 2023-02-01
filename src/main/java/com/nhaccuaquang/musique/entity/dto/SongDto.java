package com.nhaccuaquang.musique.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhaccuaquang.musique.entity.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class SongDto {
    private Long songId;
    private String name;
    private String href;
    private LocalDate releasedAt;
    private Integer songNumber;
    private Integer status;

    public SongDto(Song song) {
        this.songId = song.getSongId();
        this.name = song.getName();
        this.href = song.getHref();
        this.releasedAt = song.getReleasedAt();
        this.songNumber = song.getSongNumber();
        this.status = song.getStatus();
    }

    public SongDto(Long songId, String name, String href, LocalDate releasedAt, Integer status, Long genreId) {
        this.songId = songId;
        this.name = name;
        this.href = href;
        this.releasedAt = releasedAt;
        this.status = status;
    }
}
