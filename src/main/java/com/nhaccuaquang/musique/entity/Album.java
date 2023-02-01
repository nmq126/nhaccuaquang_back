package com.nhaccuaquang.musique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhaccuaquang.musique.util.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "albums")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    @Id
    @Column(name = "album_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    @Column(name = "type", nullable = false)
    @NotNull(message = "Type is required")
    private Long type;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "image", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Album image is required")
    private String image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "released_at", nullable = false)
    @NotNull(message = "Released date is required")
    @PastOrPresent(message = "Released date must be past or present")
    private LocalDate releasedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "artist_id", insertable = false, updatable = false)
    private Artist artist;

    @Column(name = "artist_id")
    @NotNull(message = "Artist id is required")
    private Long artistId;

    @JsonIgnore
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Song> songs;

    @Column(name = "status", nullable = false, columnDefinition = "int default 1")
    private Integer status = Constant.ACTIVE;

    @Column(name = "created_at", updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;
}
