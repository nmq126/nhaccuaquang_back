package com.nhaccuaquang.musique.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "link", columnDefinition = "TEXT")
    @NotBlank(message = "Song link is required")
    private String link;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    @NotBlank(message = "Song thumbnail is required")
    private String thumbnail;

    @Column(name = "released_at")
    @PastOrPresent(message = "Released date must be past or present")
    @Temporal(TemporalType.DATE)
    private Date releasedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private Genre genre;

    @Column(name = "genre_id")
    @NotNull(message = "Genre id is required")
    private Long genre_id;

//    @JsonIgnore
//    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<PlaylistDetail> playlistDetails = new HashSet<>();

    @Column(name = "status", columnDefinition = "boolean default true")
    private boolean status = true;

    @Column(name = "created_at", updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;
}