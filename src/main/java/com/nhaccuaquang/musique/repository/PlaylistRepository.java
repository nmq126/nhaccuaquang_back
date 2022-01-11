package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.dto.SongDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("SELECT new com.nhaccuaquang.musique.dto.SongDto(s.id, s.name, s.link, s.thumbnail, s.releasedAt, s.status, s.genre_id)" +
            " FROM Song s join PlaylistDetail pd" +
            " on s.id = pd.id.songId " +
            " join Playlist p" +
            " on pd.id.playlistId = p.id where p.id = :playlistId")
    List<SongDto> findPlaylistDetailByPlaylistId(@Param("playlistId") Long id);
}
