package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.entity.Song;
import com.nhaccuaquang.musique.specification.SearchBody;
import org.springframework.http.ResponseEntity;


public interface SongService {

    ResponseEntity findAll(SearchBody searchBody) throws Exception;
    ResponseEntity findById(Long id);
    ResponseEntity save(Song song) throws Exception;
    ResponseEntity update(Long id, Song song) throws Exception;
    ResponseEntity delete(Long id) throws Exception;
    ResponseEntity addSongToPlaylist(Long playlistId, Long songId) throws Exception;
}
