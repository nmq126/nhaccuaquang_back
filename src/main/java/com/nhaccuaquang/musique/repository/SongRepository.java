package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {
    boolean existsByAlbumIdAndSongNumber(Long albumId, Integer songNumber);
}
