package com.nhaccuaquang.musique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "playlist_details")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDetail {

    @JsonIgnore
    @EmbeddedId
    private PlaylistDetailKey id;

    @ManyToOne
    @MapsId("songId")
//    @JsonIgnore
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @MapsId("playlistId")
    @JsonIgnore
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}


