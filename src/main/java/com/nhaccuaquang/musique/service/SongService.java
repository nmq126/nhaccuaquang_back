package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.ResponseHandler;
import com.nhaccuaquang.musique.entity.Song;
import com.nhaccuaquang.musique.specification.SearchBody;


public interface SongService {

    ResponseHandler findAll(SearchBody searchBody) throws Exception;
    ResponseHandler findById(Long id);
    ResponseHandler save(Song song) throws Exception;
    ResponseHandler update(Long id, Song song) throws Exception;
    ResponseHandler delete(Long id) throws Exception;
    ResponseHandler addSongToPlaylist(Long playlistId, Long songId) throws Exception;
}
