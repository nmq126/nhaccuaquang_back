package com.nhaccuaquang.musique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {

    @Id
    @Column(name = "genre_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Genre name is required")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotBlank(message = "Genre description is required")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Song> songs;

    @Column(name = "created_at", updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;
}
