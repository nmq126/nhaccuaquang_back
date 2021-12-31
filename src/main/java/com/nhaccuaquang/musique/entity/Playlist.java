package com.nhaccuaquang.musique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Playlist name is required")
    private String name;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    @NotBlank(message = "Playlist thumbnail is required")
    private String thumbnail;

//    @JsonIgnore
//    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<PlaylistDetail> playlistDetails =new HashSet<>();

    @Column(name = "isPrivate", columnDefinition = "boolean default false")
    private boolean isPrivate = false;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;

    public void addPlaylistDetailSet(PlaylistDetail playlistDetail) {
        if (this.playlistDetails == null) {
            this.playlistDetails = new HashSet<>();
        }
        this.playlistDetails.add(playlistDetail);
    }

    public void removeSongFromPlaylist(PlaylistDetail playlistDetail) {
        this.playlistDetails.remove(playlistDetail);
    }

}
