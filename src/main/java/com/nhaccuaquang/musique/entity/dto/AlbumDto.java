package com.nhaccuaquang.musique.entity.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nhaccuaquang.musique.entity.Album;
import com.nhaccuaquang.musique.entity.enums.AlbumType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AlbumDto {
    private Long albumId;
    private String name;
    private String image;
    private String description;
    private LocalDate releasedAt;
    private String type;

    public AlbumDto(Album album){
        this.albumId = album.getAlbumId();
        this.name = album.getName();
        this.image = album.getImage();
        this.description = album.getDescription();
        this.releasedAt = album.getReleasedAt();
        this.type = AlbumType.nameOf(album.getType());
    }
}
