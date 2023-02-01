package com.nhaccuaquang.musique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "artists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    @Column(name = "artist_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Artist name is required")
    private String name;

    @Column(name = "thumbnail", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Artist thumbnail is required")
    private String thumbnail;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Album> albums;
}
