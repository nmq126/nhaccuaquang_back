package com.nhaccuaquang.musique.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhaccuaquang.musique.entity.dto.SongDto;
import com.nhaccuaquang.musique.util.Constant;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @Column(name = "song_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Song name is required")
    private String name;

    @Column(name = "href", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Song link is required")
    private String href;

    @Column(name = "released_at", nullable = false)
    @NotNull(message = "Released date is required")
    @PastOrPresent(message = "Released date must be past or present")
    private LocalDate releasedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private Genre genre;

    @Column(name = "genre_id")
    @NotNull(message = "Genre id is required")
    private Long genreId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "album_id", insertable = false, updatable = false)
    private Album album;

    @Column(name = "album_id")
    @NotNull(message = "Album id is required")
    private Long albumId;

    @Column(name = "song_number", nullable = false)
    @NotNull(message = "Number of the song in album is required")
    private Integer songNumber;

//    @JsonIgnore
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<PlaylistDetail> playlistDetails = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "song_artists",
            joinColumns = @JoinColumn(
                    name = "song_id", referencedColumnName = "song_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "artist_id", referencedColumnName = "artist_id"))
    @NotNull(message = "Artist id is required")
    private Set<Artist> artists;

    @Column(name = "status", nullable = false, columnDefinition = "int default 1")
    @NotNull(message = "Status is required")
    private Integer status = Constant.ACTIVE;

    @Column(name = "created_at", updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;

    public void addArtistToSong(Set<Artist> artists) {
        if (this.artists == null) {
            this.artists = new HashSet<>();
        }
        artists.forEach(artist -> {
            this.artists.add(artist);
        });
    }

    public Song(SongDto songDto){
        this.name = songDto.getName();
        this.href = songDto.getHref();
        this.releasedAt = songDto.getReleasedAt();
        this.status = songDto.getStatus();
    }
}