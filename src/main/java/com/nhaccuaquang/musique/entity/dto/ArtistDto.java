package com.nhaccuaquang.musique.entity.dto;

import com.nhaccuaquang.musique.entity.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArtistDto {
    private Long artistId;
    private String name;
    private String thumbnail;

    public ArtistDto(Artist artist){
        this.artistId = artist.getArtistId();
        this.name = artist.getName();
        this.thumbnail = artist.getThumbnail();
    }
}
