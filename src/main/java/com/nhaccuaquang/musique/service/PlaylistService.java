package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Playlist;
import com.nhaccuaquang.musique.entity.PlaylistDetail;
import com.nhaccuaquang.musique.entity.ResponseHandler;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface PlaylistService {
    ResponseEntity findAll(String keyword) throws Exception;
    ResponseEntity findById(Long id);
    ResponseEntity save(Playlist playlist) throws Exception;
    ResponseEntity update(Long id, Playlist playlist) throws Exception;
    ResponseEntity delete(Long id) throws Exception;
    ResponseEntity findDetailById(Long id);
    ResponseEntity findDetailById1(Long id);
//    ResponseHandler addSongToPlaylist(Long playlistId, Long songId) throws Exception;
    ResponseEntity removeSongFromPlaylist(Long playlistId, Long songId) throws Exception;
}
