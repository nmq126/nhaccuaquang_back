package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.entity.PlaylistDetail;
import com.nhaccuaquang.musique.entity.PlaylistDetailKey;
import com.nhaccuaquang.musique.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistDetailRepository extends JpaRepository<PlaylistDetail, PlaylistDetailKey> {
    List<Object> findByIdPlaylistId(Long id);
    Object findByIdPlaylistIdAndSongId(Long pid, Long sid);
    List<Object> findByIdSongId(Long id);
//    void deleteByIdPlaylistIdAndSongId(Long pid, Long sid);

    @Query(value = "delete from playlist_details where song_id = :songId and playlist_id = :playListId", nativeQuery = true)
    void deleteById(@Param("songId") Long songId, @Param("playListId") Long playListId);

}
