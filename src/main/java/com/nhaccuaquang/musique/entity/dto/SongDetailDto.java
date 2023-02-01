package com.nhaccuaquang.musique.entity.dto;

import com.nhaccuaquang.musique.entity.Artist;
import com.nhaccuaquang.musique.entity.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class SongDetailDto {
    private Long songId;
    private String name;
    private String href;
    private LocalDate releasedAt;
    private Integer songNumber;
    private Integer status;
    private AlbumDto album;
    private Set<ArtistDto> artists = new HashSet<>();

    public SongDetailDto(Song song) {
        this.songId = song.getSongId();
        this.name = song.getName();
        this.href = song.getHref();
        this.releasedAt = song.getReleasedAt();
        this.songNumber = song.getSongNumber();
        this.status = song.getStatus();
        this.album = new AlbumDto(song.getAlbum());
        song.getArtists().forEach(artist ->
                this.artists.add(new ArtistDto(artist)));

    }
}
