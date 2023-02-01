package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.entity.dto.SongDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>, JpaSpecificationExecutor<Playlist> {
    @Query("SELECT new com.nhaccuaquang.musique.entity.dto.SongDto(s.songId, s.name, s.href, s.releasedAt, s.status, s.genreId)" +
            " FROM Song s join PlaylistDetail pd" +
            " on s.songId = pd.id.songId " +
            " join Playlist p" +
            " on pd.id.playlistId = p.playlistId where p.playlistId = :playlistId")
    List<SongDto> findPlaylistDetailByPlaylistId(@Param("playlistId") Long id);
}
