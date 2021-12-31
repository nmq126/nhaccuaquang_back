package com.nhaccuaquang.musique.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
//@EqualsAndHashCode
public class PlaylistDetailKey implements Serializable {

    @Column(name = "song_id")
    private Long songId;

    @Column(name = "playlist_id")
    private Long playlistId;

    public PlaylistDetailKey(Long songId, Long playlistId) {
        this.songId = songId;
        this.playlistId = playlistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistDetailKey that = (PlaylistDetailKey) o;
        return Objects.equals(songId, that.songId) && Objects.equals(playlistId, that.playlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, playlistId);
    }
}
